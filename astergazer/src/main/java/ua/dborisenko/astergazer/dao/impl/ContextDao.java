package ua.dborisenko.astergazer.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ua.dborisenko.astergazer.dao.IContextDao;
import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@Repository
public class ContextDao implements IContextDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Context get(int id) throws DaoException {
        Context context;
        try {
            context = em.find(Context.class, id);
        } catch (Exception e) {
            throw new DaoException("Could not get the dialplan context with id " + id, e);
        } 
        if (context == null) {
            throw new RecordNotFoundException("Could not find the dialplan context with id " + id);
        }
        return context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Context> getAll() throws DaoException {
        try {
            Query query = em.createNamedQuery("Context.getAll");
            return (List<Context>) query.getResultList();
        } catch (Exception e) {
            throw new DaoException("Could not get the dialplan context list", e);
        }
    }

    @Override
    public void add(Context context) throws DuplicatedValueException, DaoException {
        try {
            em.persist(context);
        } catch (Exception e) {
            throw new DaoException("Could not add the dialplan context", e);
        }
    }

    @Override
    public long getCount(int id, String name) throws DaoException {
        try {
            Query query = em.createNamedQuery("Context.getCount");
            query.setParameter("name", name);
            query.setParameter("id", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }

    @Override
    public void update(Context context) throws DaoException {
        try {
            em.merge(context);
        } catch (Exception e) {
            throw new DaoException("Could not update the dialplan context", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Context context = get(id);
        try {
            em.remove(context);
        } catch (Exception e) {
            throw new DaoException("Could not delete the dialplan context with id " + id, e);
        }
    }
   
}
