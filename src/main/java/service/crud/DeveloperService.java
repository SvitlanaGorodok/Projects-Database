package service.crud;

import config.DatabaseManagerConnector;
import entities.dao.DeveloperDao;
import entities.dto.DeveloperDto;
import service.converter.DeveloperConverter;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DeveloperService {
    DeveloperConverter developerConverter = new DeveloperConverter();
    private final DatabaseManagerConnector manager;
    private static final String INSERT = "INSERT INTO public.developers (first_name, last_name, age, salary) VALUES(?,?,?,?);";
    private static final String UPDATE = "UPDATE public.developers SET first_name = ?, last_name = ?, age = ?, " +
            "salary = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM public.developers WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT id, first_name, last_name, age, salary FROM public.developers WHERE id = ?;";
    private static final String FIND_BY_NAME = "SELECT id, first_name, last_name, age, salary FROM public.developers WHERE first_name like ?;";
    private static final String SELECT_ALL = "SELECT id, first_name, last_name, age, salary FROM developers";
    public DeveloperService(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    public DeveloperDto create(DeveloperDto developerDto){
        DeveloperDao entity = developerConverter.convertToDao(developerDto);
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getSalary());
            statement.execute();
            entity.setId(getGeneratedKey(statement.getGeneratedKeys()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not created");
        }
        return developerDto;
    }

    public DeveloperDto update(DeveloperDto developerDto){
        DeveloperDao entity = developerConverter.convertToDao(developerDto);
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getSalary());
            statement.setInt(5, entity.getId());
            if (statement.executeUpdate() == 0){
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not updated!");
        }
        return developerDto;
    }

    public void delete(Integer developerId){
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, developerId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not deleted!");
        }
    }

    public DeveloperDto findById(Integer id){
        DeveloperDao entity = new DeveloperDao();
        ResultSet result;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            result = statement.executeQuery();
                while (result.next()) {
                    entity.setId(result.getInt("id"));
                    entity.setFirstName(result.getString("first_name"));
                    entity.setLastName(result.getString("last_name"));
                    entity.setAge(result.getInt("age"));
                    entity.setSalary(result.getInt("salary"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not found!");
        }
        return developerConverter.convertToDto(entity);
    }
    public Set<DeveloperDto> findByName(String name){
        ResultSet result;
        Set<DeveloperDto> developers = new HashSet<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)){
            statement.setString(1, "%" + name +"%");
            result = statement.executeQuery();
            while (result.next()) {
                DeveloperDto developer = new DeveloperDto();
                developer.setId(result.getInt(1));
                developer.setFirstName(result.getString(2));
                developer.setLastName(result.getString(3));
                developer.setAge(result.getInt(4));
                developer.setSalary(result.getInt(5));
                developers.add(developer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No developers found!");
        }
        return developers;
    }

    public Set<DeveloperDto> selectAll(){
        ResultSet result;
        Set<DeveloperDto> developers = new HashSet<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)){
            result = statement.executeQuery();
                while (result.next()) {
                    DeveloperDto developer = new DeveloperDto();
                    developer.setId(result.getInt(1));
                    developer.setFirstName(result.getString(2));
                    developer.setLastName(result.getString(3));
                    developer.setAge(result.getInt(4));
                    developer.setSalary(result.getInt(5));
                    developers.add(developer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No developers found!");
        }
        return developers;
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
