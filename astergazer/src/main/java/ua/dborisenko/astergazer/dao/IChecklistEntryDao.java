package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IChecklistEntryDao {

    public ChecklistEntry get(long id) throws DaoException;

    public void add(ChecklistEntry entry) throws DaoException;

    public long getCount(long id, int checklistId, String controlValue) throws DaoException;

    public void update(ChecklistEntry entry) throws DaoException;

    public void delete(long id) throws DaoException;
    
    public String getReturnValue(int checklistId, String controlValue) throws DaoException;
}
