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
public class ChecklistTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void mappingTest() {
        String expectedName = "test";
        Checklist checklist = new Checklist();
        checklist.setName(expectedName);
        ChecklistEntry entry = new ChecklistEntry();
        entry.setChecklist(checklist);

        em.persist(checklist);
        em.persist(entry);
        em.clear();
        Long id = checklist.getId();
        Checklist resultChecklist = em.find(Checklist.class, id);

        assertThat(resultChecklist.getName(), is(expectedName));
        assertThat(resultChecklist.getEntries().size(), is(1));
    }
}
