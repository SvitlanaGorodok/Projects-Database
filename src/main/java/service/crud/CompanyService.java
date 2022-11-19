package service.crud;

import config.HibernateProvider;
import entities.dao.CompanyDao;
import entities.dto.CompanyDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.converter.CompanyConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyService {
    CompanyConverter companyConverter = new CompanyConverter();
    private final HibernateProvider manager;

    public CompanyService(HibernateProvider manager) {
        this.manager = manager;
    }

    public CompanyDto create(CompanyDto companyDto){
        CompanyDao entity = companyConverter.convertToDao(companyDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companyDto;
    }

    public CompanyDto update(CompanyDto companyDto){
        CompanyDao entity = companyConverter.convertToDao(companyDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companyDto;
    }

    public void delete(CompanyDto company){
        CompanyDao entity = companyConverter.convertToDao(company);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CompanyDto findById(Integer id){
        CompanyDao entity = new CompanyDao();
        try (final Session session = manager.openSession()) {
            entity = session.get(CompanyDao.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity !=null){
            return companyConverter.convertToDto(entity);
        }
        return null;
    }
    public Set<CompanyDto> findByName(String name){
        Set<CompanyDto> companies = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM CompanyDao d WHERE d.name like :name", CompanyDao.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList()
                    .stream()
                    .map(companyConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }
    public Set<CompanyDto> selectAll(){
        Set<CompanyDto> companies = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM CompanyDao", CompanyDao.class)
                    .getResultList()
                    .stream()
                    .map(companyConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }

}
