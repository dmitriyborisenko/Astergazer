package ua.dborisenko.astergazer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IChecklistDao;
import ua.dborisenko.astergazer.dao.IChecklistEntryDao;
import ua.dborisenko.astergazer.model.Checklist;
import ua.dborisenko.astergazer.model.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IChecklistEntryService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChecklistEntryService implements IChecklistEntryService {

    @Autowired
    private IChecklistEntryDao entryDao;

    @Autowired
    private IChecklistDao checklistDao;

    private void checkIsControlValueExists(Long id, Long checklistId, String controlValue) throws ServiceException {
        long entryCount;
        try {
            entryCount = entryDao.getCount(id, checklistId, controlValue);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not perform the unique check for the checklist entry control value", e);
        }
        if (entryCount > 0) {
            throw new DuplicatedValueException("Entry with control value " + controlValue + " already exists in the checklist with id " + checklistId);
        }
    }

    @Override
    public void create(String controlValue, String returnValue, Long checklistId) throws ServiceException {
        checkIsControlValueExists(0L , checklistId, controlValue);
        try {
            Checklist checklist = checklistDao.get(checklistId);
            ChecklistEntry entry = new ChecklistEntry();
            entry.setControlValue(controlValue);
            entry.setReturnValue(returnValue);
            entry.setChecklist(checklist);
            entryDao.add(entry);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not create the checklist entry vith control value " + controlValue +
                    " inside of the checklist with id " + checklistId, e);
        }
    }

    @Override
    public void update(Long id, String controlValue, String returnValue) throws ServiceException {
        try {
            ChecklistEntry entry = entryDao.get(id);
            Checklist checklist = entry.getChecklist();
            checkIsControlValueExists(id, checklist.getId(), controlValue);
            entry.setControlValue(controlValue);
            entry.setReturnValue(returnValue);
            entryDao.update(entry);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not update the checklist entry with id " + id, e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            entryDao.delete(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not delete the checklist entry with id " + id, e);
        }
    }

    @Override
    public String getReturnValue(Long checklistId, String controlValue) throws ServiceException {
        try {
            return entryDao.getReturnValue(checklistId, controlValue);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get the checklist entry return value for control value" + controlValue, e);
        }
    }

}
