package ua.dborisenko.astergazer.dao.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.model.ConfigurationParameter.PARAM_NAME;
import ua.dborisenko.astergazer.exception.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationDaoTest {

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ConfigurationDao configurationDao;

    @Test
    public void getTest() throws DaoException {
        ConfigurationParameter expectedParameter = new ConfigurationParameter();
        PARAM_NAME name = PARAM_NAME.FASTAGI_HOST;
        expectedParameter.setName(name);
        when(mockEm.find(ConfigurationParameter.class, name)).thenReturn(expectedParameter);

        assertThat(configurationDao.get(name), is(expectedParameter));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        PARAM_NAME name = PARAM_NAME.FASTAGI_HOST;
        when(mockEm.find(ConfigurationParameter.class, name)).thenThrow(Exception.class);

        configurationDao.get(name);
    }

    @Test
    public void setTest() throws DaoException {
        ConfigurationParameter parameter = new ConfigurationParameter();

        configurationDao.set(parameter);

        verify(mockEm).merge(parameter);
    }

    @Test(expected = DaoException.class)
    public void addExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).merge(any());

        configurationDao.set(new ConfigurationParameter());
    }

}
