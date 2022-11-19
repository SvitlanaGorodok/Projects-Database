package service.crud;

import config.HibernateProvider;
import entities.dao.SkillDao;
import entities.dto.SkillDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.converter.SkillConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SkillService {
    SkillConverter skillConverter = new SkillConverter();
    private final HibernateProvider manager;

    public SkillService(HibernateProvider manager) {
        this.manager = manager;
    }

    public SkillDto create(SkillDto skillDto){
        SkillDao entity = skillConverter.convertToDao(skillDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillDto;
    }

    public SkillDto update(SkillDto skillDto){
        SkillDao entity = skillConverter.convertToDao(skillDto);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillDto;
    }

    public void delete(SkillDto skill){
        SkillDao entity = skillConverter.convertToDao(skill);
        try (final Session session = manager.openSession()) {
            final Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SkillDto findById(Integer id){
        SkillDao entity = new SkillDao();
        try (final Session session = manager.openSession()) {
            entity = session.get(SkillDao.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity !=null){
            return skillConverter.convertToDto(entity);
        }
        return null;
    }

    public Set<SkillDto> findByName(String name){
        Set<SkillDto> skills = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM SkillDao d WHERE d.area like :name", SkillDao.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList()
                    .stream()
                    .map(skillConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skills;
    }
    public Set<SkillDto> selectAll(){
        Set<SkillDto> skills = new HashSet<>();
        try (final Session session = manager.openSession()) {
            return session.createQuery("FROM SkillDao", SkillDao.class)
                    .getResultList()
                    .stream()
                    .map(skillConverter::convertToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skills;
    }

}
