package service.crud;

import config.DatabaseManagerConnector;
import entities.dao.DeveloperDao;
import entities.dao.ProjectDao;
import entities.dto.ProjectDto;
import service.converter.ProjectConverter;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ProjectService {
    ProjectConverter projectConverter = new ProjectConverter();
    private final DatabaseManagerConnector manager;
    private static final String INSERT = "INSERT INTO public.projects (name, description, start_date) VALUES(?,?,?);";
    private static final String UPDATE = "UPDATE public.projects SET name = ?, description = ?, start_date = ?  WHERE id = ?;";
    private static final String DELETE = "DELETE FROM public.projects WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT id, name, description, start_date FROM public.projects WHERE id = ?;";
    private static final String FIND_BY_NAME = "SELECT id, name, description, start_date FROM public.projects WHERE name like ?;";
    private static final String SELECT_ALL = "SELECT id, name, description, start_date FROM public.projects";

    public ProjectService(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    public ProjectDto create(ProjectDto projectDto){
        ProjectDao entity = projectConverter.convertToDao(projectDto);
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, entity.getStartDate());
            statement.execute();
            entity.setId(getGeneratedKey(statement.getGeneratedKeys()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not created");
        }
        return projectDto;
    }

    public ProjectDto update(ProjectDto projectDto){
        ProjectDao entity = projectConverter.convertToDao(projectDto);
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, entity.getStartDate());
            statement.setInt(4, entity.getId());
            if (statement.executeUpdate() == 0){
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not updated!");
        }
        return projectDto;
    }

    public void delete(Integer projectId){
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, projectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not deleted!");
        }
    }
    public ProjectDto findById(Integer id){
        ProjectDao entity = new ProjectDao();
        ResultSet result;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            result = statement.executeQuery();
                while (result.next()) {
                    entity.setId(result.getInt("id"));
                    entity.setName(result.getString("name"));
                    entity.setDescription(result.getString("description"));
                    entity.setStartDate(result.getDate("start_date"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not found!");
        }
        return projectConverter.convertToDto(entity);
    }

    public Set<ProjectDto> findByName(String name){
        ResultSet result;
        Set<ProjectDto> projects = new HashSet<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)){
            statement.setString(1, "%" + name + "%");
            result = statement.executeQuery();
                while (result.next()) {
                    ProjectDto project = new ProjectDto();
                    project.setId(result.getInt(1));
                    project.setName(result.getString(2));
                    project.setDescription(result.getString(3));
                    project.setStartDate(result.getDate(4));
                    projects.add(project);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No projects found!");
        }
        return projects;
    }

    public Set<ProjectDto> selectAll(){
        ResultSet result;
        Set<ProjectDto> projects = new HashSet<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)){
            result = statement.executeQuery();
                while (result.next()) {
                    ProjectDto project = new ProjectDto();
                    project.setId(result.getInt(1));
                    project.setName(result.getString(2));
                    project.setDescription(result.getString(3));
                    project.setStartDate(result.getDate(4));
                    projects.add(project);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No projects found!");
        }
        return projects;
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
