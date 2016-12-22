package ua.dborisenko.astergazer.service;

import java.util.Set;

import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IConfigurationService {

    public ConfigurationParameter getFastAgiHost() throws ServiceException;

    public ConfigurationParameter getModificationStamp();
    
    public Set<ConfigurationParameter> getAll() throws ServiceException;

    public void setAll(Set<ConfigurationParameter> parameters) throws ServiceException;

}
