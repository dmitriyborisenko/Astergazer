package ua.dborisenko.astergazer.model.block;

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
public class BlockTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        Block block = new Block();
        block.setCaption("testCaption");
        block.setIsLocked(true);
        block.setLocalId(1);
        block.setPosX(10);
        block.setPosY(20);
        block.setType("testType");
        BlockParameter parameter = new BlockParameter();
        parameter.setBlock(block);

        em.persist(block);
        em.persist(parameter);
        em.clear();
        long id = block.getId();
        Block resultBlock = em.find(Block.class, id);

        assertThat(resultBlock.getCaption(), is("testCaption"));
        assertThat(resultBlock.getIsLocked(), is(true));
        assertThat(resultBlock.getPosX(), is(10));
        assertThat(resultBlock.getPosY(), is(20));
        assertThat(resultBlock.getParameters().size(), is(1));

    }
}
