package ua.dborisenko.astergazer.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ua.dborisenko.astergazer.dao.IConfigurationDao;
import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.domain.ConfigurationParameter.Name;
import ua.dborisenko.astergazer.exception.DaoException;

@Repository
public class ConfigurationDao implements IConfigurationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ConfigurationParameter get(Name name) throws DaoException {
        try{
            return em.find(ConfigurationParameter.class, name);
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }

    @Override
    public void set(ConfigurationParameter parameter) throws DaoException {
        try{
            em.merge(parameter);
        } catch (Exception e) {
            throw new DaoException("Could not execute the query", e);
        }
    }
}
