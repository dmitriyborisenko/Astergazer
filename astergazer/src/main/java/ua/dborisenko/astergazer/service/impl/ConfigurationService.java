package ua.dborisenko.astergazer.service.impl;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import ua.dborisenko.astergazer.controller.ConstructorRestController;
import ua.dborisenko.astergazer.dao.IConfigurationDao;
import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.domain.ConfigurationParameter.DefaultValue;
import ua.dborisenko.astergazer.domain.ConfigurationParameter.Name;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IConfigurationService;

@Service
public class ConfigurationService implements IConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(ConstructorRestController.class);

    @Autowired
    private IConfigurationDao configurationDao;

    @Override
    public ConfigurationParameter getFastAgiHost() throws ServiceException {
        return getParameter(Name.FASTAGI_HOST, DefaultValue.localhost);
    }
    
    @Override
    public ConfigurationParameter getModificationStamp() {
        try {
            ConfigurationParameter parameter = configurationDao.get(Name.MODIFICATION_STAMP);
            if (parameter == null) {
                return new ConfigurationParameter(Name.MODIFICATION_STAMP, null);
            } else {
                return parameter;
            }
        } catch (DaoException e) {
            log.error("Could not get the modification stamp", e);
            return new ConfigurationParameter(Name.MODIFICATION_STAMP, null);
        }
    }
    
    @Override
    public Set<ConfigurationParameter> getAll() throws ServiceException {
        Set<ConfigurationParameter> result = new HashSet<>();
        result.add(getFastAgiHost());
        result.add(getModificationStamp());
        return result;
    }

    @Override
    public void setAll(Set<ConfigurationParameter> parameters) throws ServiceException {
        try {
            for (ConfigurationParameter parameter : parameters) {
                configurationDao.set(parameter);
            }
        } catch (DaoException e) {
            throw new ServiceException("Could not save the configuration parameters set", e);
        }
        setModificationStamp();
    }

    private void setModificationStamp() throws ServiceException {
        Random random = new Random();
        String stamp = System.currentTimeMillis() + "/" + random.nextLong();
        try {
            configurationDao.set(new ConfigurationParameter(Name.MODIFICATION_STAMP, stamp));
        } catch (DaoException e) {
            throw new ServiceException("Could not set modification stamp" ,e);
        }
    }
    
    private ConfigurationParameter getParameter(Name name, DefaultValue defaultValue) throws ServiceException {
        try {
            ConfigurationParameter parameter = configurationDao.get(name);
            if (parameter == null) {
                return new ConfigurationParameter(name, defaultValue.toString());
            } else {
            return parameter;
            }
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not get configuration parameter " + name, e);
        }
    }


}
