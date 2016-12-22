package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface IExtensionService {

    public void create(String name, int contextId, int scriptId) throws ServiceException;

    public void update(int id, int scriptId, String name) throws ServiceException;

    public void delete(int id) throws ServiceException;
}
