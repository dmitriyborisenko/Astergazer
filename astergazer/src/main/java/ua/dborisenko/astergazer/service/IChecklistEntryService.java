package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface IChecklistEntryService {

    public void create(String controlValue, String returnValue, int checklistId) throws ServiceException;

    public void update(long id, String controlValue, String returnValue) throws ServiceException;

    public void delete(long id) throws ServiceException;
    
    public String getReturnValue(int checklistId, String controlValue) throws ServiceException;

}
