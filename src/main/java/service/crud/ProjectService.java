package service.crud;

import config.HibernateProvider;
import entities.dao.ProjectDao;
import entities.dto.ProjectDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.converter.ProjectConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    ProjectConverter projectConverter = new ProjectConverter();
    private final HibernateProvider manager;

    public ProjectService(HibernateProvider manager) {
        this.manager = manager;
    }

    public ProjectDto create(ProjectDto projectDto){
        ProjectDao entity = projectConverter.convertToDao(projectDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectDto;
    }

    public ProjectDto update(ProjectDto projectDto){
        ProjectDao entity = projectConverter.convertToDao(projectDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectDto;
    }

    public void delete(ProjectDto project){
        ProjectDao entity = projectConverter.convertToDao(project);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ProjectDto findById(Integer id){
        ProjectDao entity = new ProjectDao();
        try (final Session session = manager.openSession()) {
            entity = session.get(ProjectDao.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity !=null){
            return projectConverter.convertToDto(entity);
        }
        return null;
    }

    public Set<ProjectDto> findByName(String name){
        Set<ProjectDto> projects = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM ProjectDao d WHERE d.name like :name", ProjectDao.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList()
                    .stream()
                    .map(projectConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }

    public Set<ProjectDto> selectAll(){
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM ProjectDao", ProjectDao.class)
                    .getResultList()
                    .stream()
                    .map(projectConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }
}
