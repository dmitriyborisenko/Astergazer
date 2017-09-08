package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.model.Checklist;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IChecklistDao {

    Checklist get(Long id) throws DaoException;

    Checklist getByName(String name) throws DaoException;

    Checklist getFull(Long id) throws DaoException;

    List<Checklist> getAll() throws DaoException;

    void add(Checklist checklist) throws DuplicatedValueException, DaoException;

    long getCount(Long id, String name) throws DaoException;

    void update(Checklist checklist) throws DaoException;

    void delete(Long id) throws DaoException;

}
