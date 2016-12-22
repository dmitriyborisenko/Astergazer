package ua.dborisenko.astergazer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import ua.dborisenko.astergazer.dao.IContextDao;
import ua.dborisenko.astergazer.dao.IExtensionDao;
import ua.dborisenko.astergazer.dao.IScriptDao;
import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IExtensionService;

@Service
public class ExtensionService implements IExtensionService {

    @Autowired
    private IContextDao contextDao;

    @Autowired
    private IExtensionDao extensionDao;

    @Autowired
    private IScriptDao scriptDao;

    private void checkIsNameExists(int id, int contextId, String name) throws ServiceException {
        long extensionCount;
        try {
            extensionCount = extensionDao.getCount(id, contextId, name);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not perform the unique check for the extension name", e);
        }
        if (extensionCount > 0) {
            throw new DuplicatedValueException(
                    "Extension with name " + name + " already exists in the context with id " + contextId);
        }
    }
    
    @Override
    public void create(String name, int contextId, int scriptId) throws ServiceException {
        checkIsNameExists(0, contextId, name);
        try {
            Context context = contextDao.get(contextId);
            Extension extension = new Extension();
            extension.setName(name);
            extension.setContext(context);
            if (scriptId != 0) {
                extension.setScript(scriptDao.get(scriptId));
            }
            extensionDao.add(extension);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException(
                    "Could not create the dialplan extension " + name + " inside of the context with id " + contextId,
                    e);
        }
    }

    @Override
    public void update(int id, int scriptId, String name) throws ServiceException {
        try {
            Extension extension = extensionDao.get(id);
            if (scriptId != 0) {
                Script script = scriptDao.get(scriptId);
                extension.setScript(script);
            } else {
                extension.setScript(null);
            }
            Context context = extension.getContext();
            checkIsNameExists(id, context.getId(), name);
            extension.setName(name);
            extensionDao.update(extension);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not update the dialplan extension with id " + id, e);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            extensionDao.delete(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not delete the dialplan extension with id " + id, e);
        }
    }
}
