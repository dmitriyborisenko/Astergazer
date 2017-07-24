package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IScriptDao {

    Script get(int id) throws DaoException;

    Script getFull(int id) throws DaoException;

    void add(Script script) throws DuplicatedValueException, DaoException;

    List<Script> getAll() throws DaoException;

    long getCount(int id, String name) throws DaoException;

    void update(Script script) throws DaoException;

    void delete(int id) throws DaoException;

}
