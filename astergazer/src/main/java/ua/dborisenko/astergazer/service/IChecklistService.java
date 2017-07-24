package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.dto.JsTreeNodeDynamicDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IChecklistService {

    void create(String name) throws ServiceException;

    List<JsTreeNodeDynamicDto> getChecklistsTreeDto() throws ServiceException;

    void update(int id, String name) throws ServiceException;

    void delete(int id) throws ServiceException;

    List<JsTreeNodeDynamicDto> getEntriesTreeDto(int checklistId) throws ServiceException;

    Checklist getByName(String name) throws ServiceException;

}
