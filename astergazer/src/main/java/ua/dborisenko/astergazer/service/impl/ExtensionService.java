package ua.dborisenko.astergazer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IContextDao;
import ua.dborisenko.astergazer.dao.IExtensionDao;
import ua.dborisenko.astergazer.dao.IScriptDao;
import ua.dborisenko.astergazer.model.Context;
import ua.dborisenko.astergazer.model.Extension;
import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IExtensionService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExtensionService implements IExtensionService {

    @Autowired
    private IContextDao contextDao;

    @Autowired
    private IExtensionDao extensionDao;

    @Autowired
    private IScriptDao scriptDao;

    private void checkIsNameExists(Long id, Long contextId, String name) throws ServiceException {
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
    public void create(String name, Long contextId, Long scriptId) throws ServiceException {
        checkIsNameExists(0L, contextId, name);
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
    public void update(Long id, Long scriptId, String name) throws ServiceException {
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
    public void delete(Long id) throws ServiceException {
        try {
            extensionDao.delete(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not delete the dialplan extension with id " + id, e);
        }
    }
}
