package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IScriptDao {

    Script get(Long id) throws DaoException;

    Script getFull(Long id) throws DaoException;

    void add(Script script) throws DuplicatedValueException, DaoException;

    List<Script> getAll() throws DaoException;

    long getCount(Long id, String name) throws DaoException;

    void update(Script script) throws DaoException;

    void delete(Long id) throws DaoException;

}
