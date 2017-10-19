package ua.dborisenko.astergazer.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ua.dborisenko.astergazer.dao.IConfigurationDao;
import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.model.ConfigurationParameter.PARAM_NAME;
import ua.dborisenko.astergazer.exception.DaoException;

@Repository
public class ConfigurationDao implements IConfigurationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<ConfigurationParameter> getAll() throws DaoException {
        try {
            return (List<ConfigurationParameter>) em.createQuery("FROM ConfigurationParameter").getResultList();
        } catch (Exception e) {
            throw new DaoException("Could not get all parameters", e);
        }
    }

    @Override
    public ConfigurationParameter get(PARAM_NAME name) throws DaoException {
        try {
            return em.find(ConfigurationParameter.class, name);
        } catch (Exception e) {
            throw new DaoException("Could not get parameter with name " + name, e);
        }
    }

    @Override
    public void set(ConfigurationParameter parameter) throws DaoException {
        try {
            em.merge(parameter);
        } catch (Exception e) {
            throw new DaoException("Could not set parameter", e);
        }
    }
}
