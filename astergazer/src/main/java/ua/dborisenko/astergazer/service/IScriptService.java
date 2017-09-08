package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.dto.ScriptDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IScriptService {

    void create(String name) throws ServiceException;

    List<JsTreeNodeDto> getScriptsTreeDto() throws ServiceException;

    List<ScriptDto> getScriptsDto() throws ServiceException;

    Script get(Long id) throws ServiceException;

    ScriptDataDto getScriptDataDto(Long id) throws ServiceException;

    void update(Long id, String name) throws ServiceException;

    void updateData(Long id, ScriptDataDto dto) throws ServiceException;

    Script clone(Long id, String name) throws ServiceException;

    void delete(Long id) throws ServiceException;

    String getModificationStamp(Long id);
}
