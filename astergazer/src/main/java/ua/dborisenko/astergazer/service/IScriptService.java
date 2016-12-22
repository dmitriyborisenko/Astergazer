package ua.dborisenko.astergazer.service;

import java.util.List;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.dto.ScriptDto;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IScriptService {

    public void create(String name) throws ServiceException;

    public List<JsTreeNodeDto> getScriptsTreeDto() throws ServiceException;
    
    public List<ScriptDto> getScriptsDto() throws ServiceException;

    public Script get(int id) throws ServiceException;

    public ScriptDataDto getScriptDataDto(int id) throws ServiceException;

    public void update(int id, String name) throws ServiceException;

    public void updateData(int id, ScriptDataDto dto) throws ServiceException;

    public void delete(int id) throws ServiceException;

    public String getModificationStamp(int id);
}
