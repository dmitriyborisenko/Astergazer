package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IChecklistEntryDao {

    ChecklistEntry get(long id) throws DaoException;

    void add(ChecklistEntry entry) throws DaoException;

    long getCount(long id, int checklistId, String controlValue) throws DaoException;

    void update(ChecklistEntry entry) throws DaoException;

    void delete(long id) throws DaoException;

    String getReturnValue(int checklistId, String controlValue) throws DaoException;
}
