package ua.dborisenko.astergazer.domain.block;

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
public class BlockParameterTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        BlockParameter parameter = new BlockParameter();
        parameter.setOrderIndex(1);
        parameter.setValue("testValue");

        em.persist(parameter);
        em.clear();
        long id = parameter.getId();
        BlockParameter resultParameter = em.find(BlockParameter.class, id);

        assertThat(resultParameter.getOrderIndex(), is(1));
        assertThat(resultParameter.getValue(), is("testValue"));
    }
}
