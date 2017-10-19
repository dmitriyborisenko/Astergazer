package ua.dborisenko.astergazer.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IConfigurationDao;
import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.model.ConfigurationParameter.PARAM_NAME;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IConfigurationService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigurationService implements IConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationService.class);
    private Map<PARAM_NAME, ConfigurationParameter> cachedParameters = new ConcurrentHashMap<>();

    @Autowired
    private IConfigurationDao configurationDao;

    @PostConstruct
    public void setUp() {
        try {
            cachedParameters = configurationDao.getAll()
                    .stream()
                    .collect(Collectors.toMap(ConfigurationParameter::getName, Function.identity()));
        } catch (CannotCreateTransactionException | DaoException e) {
            log.error("Could not upload configuration parameters to the cache.", e);
        }
    }

    @Override
    public ConfigurationParameter getFastAgiHost() {
        return getParameter(PARAM_NAME.FASTAGI_HOST);
    }

    @Override
    public ConfigurationParameter getModificationStamp() {
        return getParameter(PARAM_NAME.MODIFICATION_STAMP);
    }

    @Override
    public Collection<ConfigurationParameter> getAll() {
       return cachedParameters.values();
    }

    @Override
    public void setAll(Set<ConfigurationParameter> parameters) throws ServiceException {
        try {
            for (ConfigurationParameter parameter : parameters) {
                configurationDao.set(parameter);
            }
            cachedParameters = parameters
                    .stream()
                    .collect(Collectors.toMap(ConfigurationParameter::getName, Function.identity()));
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not save the configuration parameters set", e);
        }
    }

    private ConfigurationParameter getParameter(PARAM_NAME name) {
        ConfigurationParameter parameter = cachedParameters.get(name);
        if (parameter == null) {
            return new ConfigurationParameter(name, name.getDefaultValue());
        } else {
            return parameter;
        }
    }

}
