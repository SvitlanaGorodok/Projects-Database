package service.crud;
import config.DatabaseManagerConnector;
import entities.dao.CustomerDao;
import entities.dto.CustomerDto;
import service.converter.CustomerConverter;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CustomerService {
        CustomerConverter customerConverter = new CustomerConverter();
        private final DatabaseManagerConnector manager;
        private static final String INSERT = "INSERT INTO public.customers (name, description) VALUES(?,?);";
        private static final String UPDATE = "UPDATE public.customers SET name = ?, description = ? WHERE id = ?;";
        private static final String DELETE = "DELETE FROM public.customers WHERE id = ?;";
        private static final String FIND_BY_ID = "SELECT id, name, description FROM public.customers WHERE id = ?;";
    private static final String FIND_BY_NAME = "SELECT id, name, description FROM public.customers WHERE name like ?;";
        private static final String SELECT_ALL = "SELECT id, name, description FROM public.customers";

        public CustomerService(DatabaseManagerConnector manager) {
            this.manager = manager;
        }

        public CustomerDto create(CustomerDto customerDto){
            CustomerDao entity = customerConverter.convertToDao(customerDto);
            try (Connection connection = manager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescription());
                statement.execute();
                entity.setId(getGeneratedKey(statement.getGeneratedKeys()));
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Customer not created");
            }
            return customerDto;
        }

        public CustomerDto update(CustomerDto customerDto){
            CustomerDao entity = customerConverter.convertToDao(customerDto);
            try (Connection connection = manager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(UPDATE)){
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescription());
                statement.setInt(3, entity.getId());
                if (statement.executeUpdate() == 0){
                    return null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Customer not updated!");
            }
            return customerDto;
        }

        public void delete(Integer customerId){
            try (Connection connection = manager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE)){
                statement.setInt(1, customerId);
                statement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Customer not deleted!");
            }
        }

        public CustomerDto findById(Integer id){
            CustomerDao entity = new CustomerDao();
            ResultSet result;
            try (Connection connection = manager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
                statement.setInt(1, id);
                result = statement.executeQuery();
                    while (result.next()) {
                        entity.setId(result.getInt("id"));
                        entity.setName(result.getString("name"));
                        entity.setDescription(result.getString("description"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Customer not found!");
            }
            return customerConverter.convertToDto(entity);
        }

    public Set<CustomerDto> findByName(String name){
        ResultSet result;
        Set<CustomerDto> customers = new HashSet<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)){
            statement.setString(1, "%" + name + "%");
            result = statement.executeQuery();
            while (result.next()) {
                CustomerDto customer = new CustomerDto();
                customer.setId(result.getInt(1));
                customer.setName(result.getString(2));
                customer.setDescription(result.getString(3));
                customers.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No customers found!");
        }
        return customers;
    }
    public Set<CustomerDto> selectAll(){
        ResultSet result;
        Set<CustomerDto> customers = new HashSet<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)){
            result = statement.executeQuery();
                while (result.next()) {
                    CustomerDto customer = new CustomerDto();
                    customer.setId(result.getInt(1));
                    customer.setName(result.getString(2));
                    customer.setDescription(result.getString(3));
                    customers.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No customers found!");
        }
        return customers;
    }
    private Integer getGeneratedKey(ResultSet result){
        Integer key = null;
        try{
            while (result.next()){
                key = result.getInt(1);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Key not found!");
        }
        return key;
    }
}

