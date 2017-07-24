package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IExtensionDao {

    Extension get(int id) throws DaoException;

    void add(Extension extension) throws DaoException;

    long getCount(int id, int contextId, String name) throws DaoException;

    void update(Extension extension) throws DaoException;

    void delete(int id) throws DaoException;

    void unlinkAllFromScript(int scriptId) throws DaoException;

}
