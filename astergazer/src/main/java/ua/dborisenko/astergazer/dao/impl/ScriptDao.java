package ua.dborisenko.astergazer.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IScriptDao;
import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ScriptDao implements IScriptDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Script get(int id) throws DaoException {
        Script script;
        try {
            script = em.find(Script.class, id);
        } catch (Exception e) {
            throw new DaoException("Could not get the script with id " + id, e);
        }
        if (script == null) {
            throw new RecordNotFoundException("Could not find the script with id " + id);
        }
        return script;
    }

    @Override
    public Script getFull(int id) throws DaoException {
        Script script = get(id);
        try {
            script.getBlocks().size();
            script.getConnections().size();
        } catch (Exception e) {
            throw new DaoException("Could not get the full script with id " + id, e);
        }
        return script;
    }

    @Override
    public void add(Script script) throws DuplicatedValueException, DaoException {
        try {
            em.persist(script);
        } catch (Exception e) {
            throw new DaoException("Could not add the script", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Script> getAll() throws DaoException {
        try {
            Query query = em.createNamedQuery("Script.getAll");
            return (List<Script>) query.getResultList();
        } catch (Exception e) {
            throw new DaoException("Could not get the script list", e);
        }
    }

    @Override
    public long getCount(int id, String name) throws DaoException {
        try {
            Query query = em.createNamedQuery("Script.getCount");
            query.setParameter("name", name);
            query.setParameter("id", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }

    @Override
    public void update(Script script) throws DaoException {
        try {
            em.merge(script);
        } catch (Exception e) {
            throw new DaoException("Could not update the script", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Script script = get(id);
        try {
            em.remove(script);
        } catch (Exception e) {
            throw new DaoException("Could not delete script", e);
        }
    }
}
