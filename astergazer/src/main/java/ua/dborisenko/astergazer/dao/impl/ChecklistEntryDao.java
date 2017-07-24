package ua.dborisenko.astergazer.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ua.dborisenko.astergazer.dao.IChecklistEntryDao;
import ua.dborisenko.astergazer.domain.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@Repository
public class ChecklistEntryDao implements IChecklistEntryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ChecklistEntry get(long id) throws DaoException {
        ChecklistEntry entry;
        try {
            entry = em.find(ChecklistEntry.class, id);
        } catch (Exception e) {
            throw new DaoException("Could not get the checklist entry with id " + id, e);
        }
        if (entry == null) {
            throw new RecordNotFoundException("Could not find the checklist entry with id " + id);
        }
        return entry;
    }

    @Override
    public void add(ChecklistEntry entry) throws DaoException {
        try {
            em.persist(entry);
        } catch (Exception e) {
            throw new DaoException("Could not add the checklist entry", e);
        }
    }

    @Override
    public long getCount(long id, int checklistId, String controlValue) throws DaoException {
        try {
            Query query = em.createNamedQuery("ChecklistEntry.getCount");
            query.setParameter("controlValue", controlValue);
            query.setParameter("checklistId", checklistId);
            query.setParameter("id", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }

    @Override
    public void update(ChecklistEntry entry) throws DaoException {
        try {
            em.merge(entry);
        } catch (Exception e) {
            throw new DaoException("Could not update the checklist entry", e);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        ChecklistEntry entry = get(id);
        try {
            em.remove(entry);
            em.flush();
        } catch (Exception e) {
            throw new DaoException("Could not delete the checklist entry with id " + id, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getReturnValue(int checklistId, String controlValue) throws DaoException {
        List<String> resultList;
        try {
            Query query = em.createNamedQuery("ChecklistEntry.getReturnValue");
            query.setParameter("checklistId", checklistId);
            query.setParameter("controlValue", controlValue);
            resultList = query.getResultList();
            if (resultList.size() == 0) {
                return null;
            }
            return resultList.get(0);
        } catch (Exception e) {
            throw new DaoException("Could not get the checklist entry return value", e);
        }
    }
}
