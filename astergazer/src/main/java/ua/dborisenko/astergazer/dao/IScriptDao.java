package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IScriptDao {

    public Script get(int id) throws DaoException;

    public Script getFull(int id) throws DaoException;

    public void add(Script script) throws DuplicatedValueException, DaoException;

    public List<Script> getAll() throws DaoException;

    public long getCount(int id, String name) throws DaoException;

    public void update(Script script) throws DaoException;

    public void delete(int id) throws DaoException;

}
