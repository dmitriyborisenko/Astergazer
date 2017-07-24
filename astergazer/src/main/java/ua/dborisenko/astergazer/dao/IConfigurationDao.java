package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.domain.ConfigurationParameter.PARAM_NAME;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IConfigurationDao {

    ConfigurationParameter get(PARAM_NAME parameter) throws DaoException;

    void set(ConfigurationParameter parameter) throws DaoException;
}
