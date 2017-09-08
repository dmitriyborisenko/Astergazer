package ua.dborisenko.astergazer.model;

import static org.hamcrest.CoreMatchers.is;
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
public class ContextTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        String expectedName = "test";
        Context context = new Context();
        context.setName(expectedName);
        Extension extension = new Extension();
        extension.setContext(context);

        em.persist(context);
        em.persist(extension);
        em.clear();
        Long id = context.getId();
        Context resultContext = em.find(Context.class, id);

        assertThat(resultContext.getName(), is(expectedName));
        assertThat(resultContext.getExtensions().size(), is(1));
    }
}
