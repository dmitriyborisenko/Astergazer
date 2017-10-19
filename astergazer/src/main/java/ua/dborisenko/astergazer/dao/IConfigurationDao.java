package ua.dborisenko.astergazer.dao;

import java.util.List;

import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.model.ConfigurationParameter.PARAM_NAME;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IConfigurationDao {

    List<ConfigurationParameter> getAll() throws DaoException;

    ConfigurationParameter get(PARAM_NAME parameter) throws DaoException;

    void set(ConfigurationParameter parameter) throws DaoException;
}
