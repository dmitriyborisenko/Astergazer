package ua.dborisenko.astergazer.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IChecklistDao;
import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ChecklistDao implements IChecklistDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Checklist get(int id) throws DaoException {
        Checklist checklist;
        try {
            checklist = em.find(Checklist.class, id);
        } catch (Exception e) {
            throw new DaoException("Could not get the checklist with id " + id, e);
        }
        if (checklist == null) {
            throw new RecordNotFoundException("Could not find the checklist with id " + id);
        }
        return checklist;
    }

    @Override
    public Checklist getByName(String name) throws DaoException {
        try {
            Query query = em.createNamedQuery("Checklist.getByName");
            query.setParameter("name", name);
            return (Checklist) query.getSingleResult();
        } catch (NoResultException e) {
            throw new RecordNotFoundException("Could not find the checklist with name " + name);
        } catch (Exception e) {
            throw new DaoException("Could not get the checklist with name " + name, e);
        }
    }

    @Override
    public Checklist getFull(int id) throws DaoException {
        Checklist checklist = get(id);
        try {
            checklist.getEntries().size();
        } catch (Exception e) {
            throw new DaoException("Could not get entries for the checklist with id " + id, e);
        }
        return checklist;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Checklist> getAll() throws DaoException {
        try {
            Query query = em.createNamedQuery("Checklist.getAll");
            return (List<Checklist>) query.getResultList();
        } catch (Exception e) {
            throw new DaoException("Could not get the list of checklists", e);
        }
    }

    @Override
    public void add(Checklist checklist) throws DuplicatedValueException, DaoException {
        try {
            em.persist(checklist);
        } catch (Exception e) {
            throw new DaoException("Could not add the checklist", e);
        }
    }

    @Override
    public long getCount(int id, String name) throws DaoException {
        try {
            Query query = em.createNamedQuery("Checklist.getCount");
            query.setParameter("name", name);
            query.setParameter("id", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }

    @Override
    public void update(Checklist checklist) throws DaoException {
        try {
            em.merge(checklist);
        } catch (Exception e) {
            throw new DaoException("Could not update the checklist", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Checklist checklist = get(id);
        try {
            em.remove(checklist);
            em.flush();
        } catch (Exception e) {
            throw new DaoException("Could not delete the checklist with id " + id, e);
        }
    }

}
