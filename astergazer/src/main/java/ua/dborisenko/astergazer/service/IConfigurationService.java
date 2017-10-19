package ua.dborisenko.astergazer.service;

import java.util.Collection;
import java.util.Set;

import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IConfigurationService {

    ConfigurationParameter getFastAgiHost();

    ConfigurationParameter getModificationStamp();

    Collection<ConfigurationParameter> getAll();

    void setAll(Set<ConfigurationParameter> parameters) throws ServiceException;

}
