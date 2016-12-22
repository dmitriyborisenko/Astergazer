package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.domain.ConfigurationParameter.Name;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IConfigurationDao {

    public ConfigurationParameter get(Name name) throws DaoException;

    public void set(ConfigurationParameter parameter) throws DaoException;
}
