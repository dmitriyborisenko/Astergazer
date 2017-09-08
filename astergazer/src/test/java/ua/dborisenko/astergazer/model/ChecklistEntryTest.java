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
public class ChecklistEntryTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        String expectedControlValue = "test1";
        String expectedReturnValue = "test2";
        ChecklistEntry entry = new ChecklistEntry();
        entry.setControlValue(expectedControlValue);
        entry.setReturnValue(expectedReturnValue);

        em.persist(entry);
        em.clear();
        long id = entry.getId();
        ChecklistEntry resultEntry = em.find(ChecklistEntry.class, id);

        assertThat(resultEntry.getControlValue(), is(expectedControlValue));
        assertThat(resultEntry.getReturnValue(), is(expectedReturnValue));
    }
}
