package service.crud;
import config.HibernateProvider;
import entities.dao.CustomerDao;
import entities.dto.CustomerDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.converter.CustomerConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerService {
        CustomerConverter customerConverter = new CustomerConverter();
        private final HibernateProvider manager;

        public CustomerService(HibernateProvider manager) {
            this.manager = manager;
        }

        public CustomerDto create(CustomerDto customerDto){
            CustomerDao entity = customerConverter.convertToDao(customerDto);
            try (final Session session = manager.openSession()) {
                final Transaction transaction = session.beginTransaction();
                session.save(entity);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customerDto;
        }

        public CustomerDto update(CustomerDto customerDto){
            CustomerDao entity = customerConverter.convertToDao(customerDto);
            try (final Session session = manager.openSession()) {
                final Transaction transaction = session.beginTransaction();
                session.update(entity);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customerDto;
        }

        public void delete(CustomerDto customer){
            CustomerDao entity = customerConverter.convertToDao(customer);
            try (final Session session = manager.openSession()) {
                final Transaction transaction = session.beginTransaction();
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public CustomerDto findById(Integer id){
            CustomerDao entity = new CustomerDao();
            try (final Session session = manager.openSession()) {
                entity = session.get(CustomerDao.class, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (entity !=null){
                return customerConverter.convertToDto(entity);
            }
            return null;
        }

    public Set<CustomerDto> findByName(String name){
        Set<CustomerDto> customers = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM CustomerDao d WHERE d.name like :name", CustomerDao.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList()
                    .stream()
                    .map(customerConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    public Set<CustomerDto> selectAll(){
        Set<CustomerDto> customers = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM CustomerDao", CustomerDao.class)
                    .getResultList()
                    .stream()
                    .map(customerConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
}

