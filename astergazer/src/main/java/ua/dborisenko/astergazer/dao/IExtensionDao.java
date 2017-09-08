package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.model.Extension;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IExtensionDao {

    Extension get(Long id) throws DaoException;

    void add(Extension extension) throws DaoException;

    long getCount(Long id, Long contextId, String name) throws DaoException;

    void update(Extension extension) throws DaoException;

    void delete(Long id) throws DaoException;

    void unlinkAllExtensionsFromScript(Long scriptId) throws DaoException;

}
