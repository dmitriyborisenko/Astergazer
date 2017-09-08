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
import ua.dborisenko.astergazer.model.block.Block;

@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ScriptTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        String expectedName = "test";
        Script script = new Script();
        script.setName(expectedName);
        Block block = new Block();
        block.setScript(script);
        Connection connection = new Connection();
        connection.setScript(script);

        em.persist(script);
        em.persist(block);
        em.persist(connection);
        em.clear();
        Long id = script.getId();
        Script resultScript = em.find(Script.class, id);

        assertThat(resultScript.getName(), is(expectedName));
        assertThat(resultScript.getBlocks().size(), is(1));
        assertThat(resultScript.getConnections().size(), is(1));
    }
}
