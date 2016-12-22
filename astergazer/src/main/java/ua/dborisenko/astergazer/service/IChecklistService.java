package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.dto.JsTreeNodeDynamicDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IChecklistService {

    public void create(String name) throws ServiceException;

    public List<JsTreeNodeDynamicDto> getChecklistsTreeDto() throws ServiceException;

    public void update(int id, String name) throws ServiceException;

    public void delete(int id) throws ServiceException;

    public List<JsTreeNodeDynamicDto> getEntriesTreeDto(int checklistId) throws ServiceException;
    
    public Checklist getByName(String name) throws ServiceException;
    
}
