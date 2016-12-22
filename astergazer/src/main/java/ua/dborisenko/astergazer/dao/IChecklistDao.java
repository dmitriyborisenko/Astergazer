package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;

public interface IChecklistDao {

    public Checklist get(int id) throws DaoException;
    
    public Checklist getByName(String name) throws DaoException;
  
    public Checklist getFull(int id) throws DaoException;

    public List<Checklist> getAll() throws DaoException;

    public void add(Checklist checklist) throws DuplicatedValueException, DaoException;

    public long getCount(int id, String name) throws DaoException;

    public void update(Checklist checklist) throws DaoException;

    public void delete(int id) throws DaoException;

}
