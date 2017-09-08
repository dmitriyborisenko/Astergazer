package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface IChecklistEntryService {

    void create(String controlValue, String returnValue, Long checklistId) throws ServiceException;

    void update(Long id, String controlValue, String returnValue) throws ServiceException;

    void delete(Long id) throws ServiceException;

    String getReturnValue(Long checklistId, String controlValue) throws ServiceException;

}
