package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.dto.ScriptDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IScriptService {

    void create(String name) throws ServiceException;

    List<JsTreeNodeDto> getScriptsTreeDto() throws ServiceException;

    List<ScriptDto> getScriptsDto() throws ServiceException;

    Script get(int id) throws ServiceException;

    ScriptDataDto getScriptDataDto(int id) throws ServiceException;

    void update(int id, String name) throws ServiceException;

    void updateData(int id, ScriptDataDto dto) throws ServiceException;

    void delete(int id) throws ServiceException;

    String getModificationStamp(int id);
}
