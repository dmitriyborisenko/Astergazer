package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IContextService {

    public void create(String name) throws ServiceException;

    public List<JsTreeNodeDto> getContextsTreeDto() throws ServiceException;

    public void update(int id, String name) throws ServiceException;

    public void delete(int id) throws ServiceException;
}
