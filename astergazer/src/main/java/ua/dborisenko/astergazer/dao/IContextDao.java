package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IContextDao {

    public Context get(int id) throws DaoException;

    public List<Context> getAll() throws DaoException;

    public void add(Context context) throws DuplicatedValueException, DaoException;

    public long getCount(int id, String name) throws DaoException;

    public void update(Context context) throws DaoException;

    public void delete(int id) throws DaoException;

}
