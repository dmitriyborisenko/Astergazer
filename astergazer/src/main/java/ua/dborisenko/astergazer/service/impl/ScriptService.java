package ua.dborisenko.astergazer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IExtensionDao;
import ua.dborisenko.astergazer.dao.IScriptDao;
import ua.dborisenko.astergazer.model.Connection;
import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.model.block.Block;
import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.dto.ScriptDto;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IBlockService;
import ua.dborisenko.astergazer.service.IScriptService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ScriptService implements IScriptService {

    private static final Logger log = LoggerFactory.getLogger(ScriptService.class);

    @Autowired
    private IScriptDao scriptDao;

    @Autowired
    private IBlockService blockService;

    @Autowired
    private IExtensionDao extensionDao;

    private void checkIsNameExists(Long id, String name) throws ServiceException {
        long scriptCount;
        try {
            scriptCount = scriptDao.getCount(id, name);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not perform the unique check for the script name", e);
        }
        if (scriptCount > 0) {
            throw new DuplicatedValueException("Script with name " + name + " already exists.");
        }
    }

    @Override
    public void create(String name) throws ServiceException {
        checkIsNameExists(0L, name);
        Script script = createEmptyScript(name);
        blockService.addStartBlockToScript(script);
    }

    private Script createEmptyScript(String name) throws ServiceException {
        Script script = new Script();
        script.setName(name);
        try {
            scriptDao.add(script);
            return script;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not create an empty script", e);
        }
    }

    @Override
    public List<JsTreeNodeDto> getScriptsTreeDto() throws ServiceException {
        try {
            List<JsTreeNodeDto> dtoList = new ArrayList<>();
            for (Script script : scriptDao.getAll()) {
                dtoList.add(new JsTreeNodeDto(script));
            }
            return dtoList;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get the script list", e);
        }
    }

    @Override
    public List<ScriptDto> getScriptsDto() throws ServiceException {
        try {
            List<ScriptDto> dtoList = new ArrayList<>();
            for (Script script : scriptDao.getAll()) {
                dtoList.add(new ScriptDto(script));
            }
            return dtoList;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get the script list", e);
        }
    }

    @Override
    public Script get(Long id) throws ServiceException {
        try {
            return scriptDao.get(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not load the script with id " + id, e);
        }
    }

    @Override
    public ScriptDataDto getScriptDataDto(Long id) throws ServiceException {
        try {
            Script script = scriptDao.getFull(id);
            ScriptDataDto dto = new ScriptDataDto(script);
            dto.setModificationStamp(script.getModificationStamp());
            return dto;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not load data for the script with id " + id, e);
        }
    }

    @Override
    public void update(Long id, String name) throws ServiceException {
        checkIsNameExists(id, name);
        try {
            Script script = scriptDao.get(id);
            script.setName(name);
            scriptDao.update(script);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not rename the script with id " + id + " to '" + name + "'", e);
        }
    }

    @Override
    public void updateData(Long id, ScriptDataDto dto) throws ServiceException {
        try {
            Script script = scriptDao.getFull(id);
            script.getBlocks().clear();
            for (Block block : dto.getBlocks()) {
                block.setScript(script);
                script.getBlocks().add(block);
            }
            script.getConnections().clear();
            for (Connection connection : dto.getConnections()) {
                connection.setScript(script);
                script.getConnections().add(connection);
            }
            script.setModificationStamp(getNewModificationStamp());
            scriptDao.update(script);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not update script with id " + id, e);
        }
    }

    @Override
    public Script clone(Long id, String name) throws ServiceException {
        try {
            Script originalScript = scriptDao.getFull(id);
            Script newScript = (Script) originalScript.clone();
            newScript.setName(name);
            scriptDao.add(newScript);
            return newScript;
        } catch (CloneNotSupportedException | CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not clone script with id " + id, e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            extensionDao.unlinkAllExtensionsFromScript(id);
            scriptDao.delete(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not delete script with id " + id, e);
        }
    }

    private String getNewModificationStamp() {
        Random random = new Random();
        return System.currentTimeMillis() + "/" + random.nextLong();
    }

    @Override
    public String getModificationStamp(Long id) {
        try {
            return scriptDao.get(id).getModificationStamp();
        } catch (DaoException e) {
            log.error("Could not get the modification stamp for the script with id {}", id, e);
            return null;
        }
    }
}
