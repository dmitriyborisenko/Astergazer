package ua.dborisenko.astergazer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import ua.dborisenko.astergazer.dao.IChecklistDao;
import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.domain.ChecklistEntry;
import ua.dborisenko.astergazer.dto.JsTreeNodeDynamicDto;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IChecklistService;

@Service
public class ChecklistService implements IChecklistService {

    @Autowired
    private IChecklistDao checklistDao;

    private void checkIsNameExists(int id, String name) throws ServiceException {
        long checklistCount;
        try {
            checklistCount = checklistDao.getCount(id, name);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not perform the unique check for the checklist name", e);
        }
        if (checklistCount > 0) {
            throw new DuplicatedValueException("Checklist with name " + name + " already exists");
        }
    }
    
    @Override
    public Checklist getByName(String name) throws ServiceException {
        try {
            return checklistDao.getByName(name);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not perform the check for the checklist existence with name " + name, e);
        }    
    }

    @Override
    public void create(String name) throws ServiceException {
        checkIsNameExists(0, name);
        try {
            Checklist checklist = new Checklist();
            checklist.setName(name);
            checklistDao.add(checklist);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not create the checklist " + name, e);
        }
    }

    @Override
    public List<JsTreeNodeDynamicDto> getChecklistsTreeDto() throws ServiceException {
        try {
            List<JsTreeNodeDynamicDto> dtoList = new ArrayList<>();
            for (Checklist checklist : checklistDao.getAll()) {
                dtoList.add(new JsTreeNodeDynamicDto(checklist));
            }
            return dtoList;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get the list of checklists", e);
        }
    }

    @Override
    public void update(int id, String name) throws ServiceException {
        try {
            Checklist checklist = checklistDao.get(id);
            checklist.setName(name);
            checklistDao.update(checklist);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not update the checklist with id " + id, e);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            checklistDao.delete(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not delete the checklist with id " + id, e);
        }
    }
    
    @Override
    public List<JsTreeNodeDynamicDto> getEntriesTreeDto(int checklistId) throws ServiceException {
        try {
            Checklist checklist = checklistDao.getFull(checklistId);
            List<JsTreeNodeDynamicDto> dtoList = new ArrayList<>();
            for (ChecklistEntry entry : checklist.getEntries()) {
                dtoList.add(new JsTreeNodeDynamicDto(entry));
            }
            return dtoList;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get the full checklist with id " + checklistId, e);
        }
    }
}
