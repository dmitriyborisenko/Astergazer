package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IExtensionDao {

    public Extension get(int id) throws DaoException;

    public void add(Extension extension) throws DaoException;

    public long getCount(int id, int contextId, String name) throws DaoException;

    public void update(Extension extension) throws DaoException;

    public void delete(int id) throws DaoException;
    
    public void unlinkAllFromScript(int scriptId) throws DaoException;

}
