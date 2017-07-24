package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface IChecklistEntryService {

    void create(String controlValue, String returnValue, int checklistId) throws ServiceException;

    void update(long id, String controlValue, String returnValue) throws ServiceException;

    void delete(long id) throws ServiceException;

    String getReturnValue(int checklistId, String controlValue) throws ServiceException;

}
