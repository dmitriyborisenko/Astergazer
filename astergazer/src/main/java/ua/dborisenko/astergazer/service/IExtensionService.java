package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface IExtensionService {

    void create(String name, Long contextId, Long scriptId) throws ServiceException;

    void update(Long id, Long scriptId, String name) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
