package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.model.Checklist;
import ua.dborisenko.astergazer.dto.JsTreeNodeDynamicDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IChecklistService {

    void create(String name) throws ServiceException;

    List<JsTreeNodeDynamicDto> getChecklistsTreeDto() throws ServiceException;

    void update(Long id, String name) throws ServiceException;

    void delete(Long id) throws ServiceException;

    List<JsTreeNodeDynamicDto> getEntriesTreeDto(Long checklistId) throws ServiceException;

    Checklist getByName(String name) throws ServiceException;

}
