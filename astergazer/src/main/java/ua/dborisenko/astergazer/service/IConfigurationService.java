package ua.dborisenko.astergazer.service;

import java.util.Set;

import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IConfigurationService {

    ConfigurationParameter getFastAgiHost() throws ServiceException;

    ConfigurationParameter getModificationStamp();

    Set<ConfigurationParameter> getAll() throws ServiceException;

    void setAll(Set<ConfigurationParameter> parameters) throws ServiceException;

}
