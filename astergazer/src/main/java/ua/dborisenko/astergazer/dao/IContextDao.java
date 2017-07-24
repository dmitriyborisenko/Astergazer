package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IContextDao {

    Context get(int id) throws DaoException;

    List<Context> getAll() throws DaoException;

    void add(Context context) throws DuplicatedValueException, DaoException;

    long getCount(int id, String name) throws DaoException;

    void update(Context context) throws DaoException;

    void delete(int id) throws DaoException;

}
