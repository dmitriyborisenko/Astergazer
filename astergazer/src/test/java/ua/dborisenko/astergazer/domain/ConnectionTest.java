package ua.dborisenko.astergazer.domain;

import static org.hamcrest.CoreMatchers.*;
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
public class ConnectionTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        int expectedSourceId = 1;
        int expectedTargetId = 2;
        Connection connection = new Connection();
        connection.setIsLocked(true);
        connection.setSourceBlockLocalId(expectedSourceId);
        connection.setTargetBlockLocalId(expectedTargetId);
        em.persist(connection);
        em.clear();
        long id = connection.getId();
        Connection resultConnection = em.find(Connection.class, id);

        assertThat(resultConnection.getIsLocked(), is(true));
        assertThat(resultConnection.getSourceBlockLocalId(), is(expectedSourceId));
        assertThat(resultConnection.getTargetBlockLocalId(), is(expectedTargetId));
    }
}
