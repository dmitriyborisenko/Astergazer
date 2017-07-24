package ua.dborisenko.astergazer.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ua.dborisenko.astergazer.dao.IExtensionDao;
import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@Repository
public class ExtensionDao implements IExtensionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Extension get(int id) throws DaoException {
        Extension extension;
        try {
            extension = em.find(Extension.class, id);
        } catch (Exception e) {
            throw new DaoException("Could not get the dialplan extension with id " + id, e);
        }
        if (extension == null) {
            throw new RecordNotFoundException("Could not find the dialplan extension with id " + id);
        }
        return extension;
    }

    @Override
    public void add(Extension extension) throws DaoException {
        try {
            em.persist(extension);
        } catch (Exception e) {
            throw new DaoException("Could not add the dialplan extension", e);
        }
    }

    @Override
    public long getCount(int id, int contextId, String name) throws DaoException {
        try {
            Query query = em.createNamedQuery("Extension.getCount");
            query.setParameter("name", name);
            query.setParameter("contextId", contextId);
            query.setParameter("id", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }

    @Override
    public void update(Extension extension) throws DaoException {
        try {
            em.merge(extension);
        } catch (Exception e) {
            throw new DaoException("Could not update the dialplan extension", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Extension extension = get(id);
        try {
            extension.getContext().getExtensions().remove(extension);
            em.remove(extension);
            em.flush();
        } catch (Exception e) {
            throw new DaoException("Could not delete the dialplan extension with id " + id, e);
        }
    }

    @Override
    public void unlinkAllFromScript(int scriptId) throws DaoException {
        try {
            Query query = em.createNamedQuery("Extension.unlinkAllFromScript");
            query.setParameter("id", scriptId);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(
                    "Could not nullify the script_id column in the extensions table for the value " + scriptId, e);
        }
    }
}
