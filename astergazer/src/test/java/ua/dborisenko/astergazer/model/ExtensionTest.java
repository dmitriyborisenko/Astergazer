package ua.dborisenko.astergazer.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ExtensionTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        String expectedName = "test";
        Extension extension = new Extension();
        extension.setName(expectedName);
        Script script = new Script();
        extension.setScript(script);

        em.persist(script);
        em.persist(extension);
        em.clear();
        Long id = extension.getId();
        Extension resultExtension = em.find(Extension.class, id);

        assertThat(resultExtension.getName(), is(expectedName));
        assertThat(resultExtension.getScript(), is(notNullValue()));
    }
}
