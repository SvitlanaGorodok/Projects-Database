package service.crud;
import config.DatabaseManagerConnector;
import entities.dao.CustomerDao;
import entities.dto.CustomerDto;
import service.converter.CustomerConverter;

import java.sql.*;

public class CustomerService {
        CustomerConverter customerConverter = new CustomerConverter();
        private final DatabaseManagerConnector manager;
        private static final String INSERT = "INSERT INTO public.customers (name, description) VALUES(?,?);";
        private static final String UPDATE = "UPDATE public.customers SET name = ?, description = ? WHERE id = ?;";
        private static final String DELETE = "DELETE FROM public.customers WHERE id = ? AND name = ? AND description = ?;";
        private static final String FIND_BY_ID = "SELECT id, name, description FROM public.customers WHERE id = ?;";

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
                System.out.println("Customer successfully created!");
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
                    System.out.println("Customer not updated!");
                } else {
                    System.out.println("Customer successfully updated!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Customer not updated!");
            }
            return customerDto;
        }

        public void delete(CustomerDto customerDto){
            CustomerDao entity = customerConverter.convertToDao(customerDto);
            try (Connection connection = manager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE)){
                statement.setInt(1, entity.getId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getDescription());
                if (statement.executeUpdate() == 0){
                    System.out.println("Customer not deleted!");
                } else {
                    System.out.println("Customer successfully deleted!");
                }
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
                if (!result.isBeforeFirst()){
                    System.out.println("Customer not found!");
                } else {
                    while (result.next()) {
                        entity.setId(result.getInt("id"));
                        entity.setName(result.getString("name"));
                        entity.setDescription(result.getString("description"));
                        System.out.println(customerConverter.convertToDto(entity).toString());
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Customer not found!");
            }
            return customerConverter.convertToDto(entity);
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

