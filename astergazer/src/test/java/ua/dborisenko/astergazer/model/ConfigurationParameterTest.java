package ua.dborisenko.astergazer.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.dborisenko.astergazer.model.ConfigurationParameter.PARAM_NAME;

@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ConfigurationParameterTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        PARAM_NAME name = PARAM_NAME.FASTAGI_HOST;
        String expectedValue = "test";
        ConfigurationParameter parameter = new ConfigurationParameter();
        parameter.setName(name);
        parameter.setValue(expectedValue);

        em.merge(parameter);
        ConfigurationParameter resultParameter = em.find(ConfigurationParameter.class, name);

        assertThat(resultParameter.getValue(), is(expectedValue));
    }
}
