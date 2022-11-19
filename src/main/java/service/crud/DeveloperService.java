package service.crud;

import config.HibernateProvider;
import entities.dao.DeveloperDao;
import entities.dto.DeveloperDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.converter.DeveloperConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperService {
    DeveloperConverter developerConverter = new DeveloperConverter();
    private final HibernateProvider manager;
    public DeveloperService(HibernateProvider manager) {
        this.manager = manager;
    }

    public DeveloperDto create(DeveloperDto developerDto){
        DeveloperDao entity = developerConverter.convertToDao(developerDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return developerDto;
    }

    public DeveloperDto update(DeveloperDto developerDto){
        DeveloperDao entity = developerConverter.convertToDao(developerDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return developerDto;
    }

    public void delete(DeveloperDto developerDto){
        DeveloperDao entity = developerConverter.convertToDao(developerDto);
            try (final Session session = manager.openSession()) {
                final Transaction transaction = session.beginTransaction();
                    session.remove(entity);
                    transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public DeveloperDto findById(Integer id){
        DeveloperDao entity = new DeveloperDao();
        try (final Session session = manager.openSession()) {
            entity = session.get(DeveloperDao.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity !=null){
            return developerConverter.convertToDto(entity);
        }
        return null;
    }
    public Set<DeveloperDto> findByName(String name){
        Set<DeveloperDto> developers = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM DeveloperDao d WHERE d.firstName like :first OR d.lastName like :last", DeveloperDao.class)
                    .setParameter("first", "%" + name + "%")
                    .setParameter("last", "%" + name + "%")
                    .getResultList()
                    .stream()
                    .map(developerConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return developers;
    }
    public Set<DeveloperDto> selectAll(){
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM DeveloperDao", DeveloperDao.class)
                    .getResultList()
                    .stream()
                    .map(developerConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }
}
