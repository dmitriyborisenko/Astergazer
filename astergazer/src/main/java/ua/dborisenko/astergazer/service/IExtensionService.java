package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface IExtensionService {

    void create(String name, int contextId, int scriptId) throws ServiceException;

    void update(int id, int scriptId, String name) throws ServiceException;

    void delete(int id) throws ServiceException;
}
