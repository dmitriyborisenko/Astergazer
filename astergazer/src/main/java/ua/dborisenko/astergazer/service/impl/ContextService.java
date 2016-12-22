package ua.dborisenko.astergazer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import ua.dborisenko.astergazer.dao.IContextDao;
import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IContextService;

@Service
public class ContextService implements IContextService {

    @Autowired
    private IContextDao contextDao;

    private void checkIsNameExists(int id, String name) throws ServiceException {
        long contextCount;
        try {
            contextCount = contextDao.getCount(id, name);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not perform the unique check for the dialplan context name", e);
        }
        if (contextCount > 0) {
            throw new DuplicatedValueException(
                    "The context with name " + name + " already exists in the dialplan " + id);
        }
    }

    @Override
    public void create(String name) throws ServiceException {
        checkIsNameExists(0, name);
        Context context = new Context();
        context.setName(name);
        try {
            contextDao.add(context);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not create the dialplan context with name " + name, e);
        }
    }

    @Override
    public List<JsTreeNodeDto> getContextsTreeDto() throws ServiceException {
        try {
            List<JsTreeNodeDto> dtoList = new ArrayList<>();
            for (Context context : contextDao.getAll()) {
                dtoList.add(new JsTreeNodeDto(context));
                for (Extension extension : context.getExtensions()) {
                    dtoList.add(new JsTreeNodeDto(extension));
                }
            }
            return dtoList;
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get the dialplan context list", e);
        }
    }

    @Override
    public void update(int id, String name) throws ServiceException {
        checkIsNameExists(id, name);
        try {
            Context context = contextDao.get(id);
            context.setName(name);
            contextDao.update(context);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not update the dialplan context name", e);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            contextDao.delete(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not delete dialplan context", e);
        }
    }
}
