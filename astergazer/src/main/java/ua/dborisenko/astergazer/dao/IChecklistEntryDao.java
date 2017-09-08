package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.model.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IChecklistEntryDao {

    ChecklistEntry get(Long id) throws DaoException;

    void add(ChecklistEntry entry) throws DaoException;

    long getCount(Long id, Long checklistId, String controlValue) throws DaoException;

    void update(ChecklistEntry entry) throws DaoException;

    void delete(Long id) throws DaoException;

    String getReturnValue(Long checklistId, String controlValue) throws DaoException;
}
