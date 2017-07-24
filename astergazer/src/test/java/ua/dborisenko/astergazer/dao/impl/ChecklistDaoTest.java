package ua.dborisenko.astergazer.dao.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.domain.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ChecklistDaoTest {

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ChecklistDao checklistDao;

    @InjectMocks
    @Spy
    private ChecklistDao spyChecklistDao;

    @Test
    public void getTest() throws DaoException {
        int id = 1;
        Checklist expectedCheckList = new Checklist();
        expectedCheckList.setId(id);

        when(mockEm.find(Checklist.class, id)).thenReturn(expectedCheckList);

        assertThat(checklistDao.get(id), is(expectedCheckList));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Checklist.class, id)).thenThrow(Exception.class);
        checklistDao.get(id);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getNotFoundTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Checklist.class, id)).thenReturn(null);
        checklistDao.get(id);
    }

    @Test
    public void getByNameTest() throws DaoException {
        int id = 1;
        String name = "test";
        Checklist expectedCheckList = new Checklist();
        expectedCheckList.setId(id);
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Checklist.getByName")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedCheckList);

        assertThat(checklistDao.getByName(name), is(expectedCheckList));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getByNameExceptionTest() throws DaoException {
        String name = "test";

        when(mockEm.createNamedQuery("Checklist.getByName")).thenThrow(Exception.class);
        checklistDao.getByName(name);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = RecordNotFoundException.class)
    public void getByNameNotFoundTest() throws DaoException {
        String name = "test";
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Checklist.getByName")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenThrow(NoResultException.class);
        checklistDao.getByName(name);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getFullTest() throws DaoException {
        int id = 1;
        Checklist mockChecklist = mock(Checklist.class);
        List<ChecklistEntry> mockEntryList = mock(ArrayList.class);

        when(mockChecklist.getEntries()).thenReturn(mockEntryList);
        doReturn(mockChecklist).when(spyChecklistDao).get(id);
        spyChecklistDao.getFull(id);

        verify(spyChecklistDao).get(id);
        verify(mockEntryList).size();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getFullExceptionTest() throws DaoException {
        int id = 1;
        Checklist mockChecklist = mock(Checklist.class);
        List<ChecklistEntry> mockEntryList = mock(ArrayList.class);

        when(mockChecklist.getEntries()).thenReturn(mockEntryList);
        when(mockEntryList.size()).thenThrow(Exception.class);
        doReturn(mockChecklist).when(spyChecklistDao).get(id);
        spyChecklistDao.getFull(id);
    }

    @Test
    public void getAllTest() throws DaoException {
        List<Checklist> expectedList = new ArrayList<>();
        expectedList.add(new Checklist());
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Checklist.getAll")).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);

        assertThat(checklistDao.getAll(), is(expectedList));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getAllExceptionTest() throws DaoException {
        when(mockEm.createNamedQuery("Checklist.getAll")).thenThrow(Exception.class);
        checklistDao.getAll();
    }

    @Test
    public void addTest() throws DaoException {
        Checklist checklist = new Checklist();
        checklistDao.add(checklist);

        verify(mockEm).persist(checklist);
    }

    @Test(expected = DaoException.class)
    public void addExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).persist(any());
        checklistDao.add(new Checklist());
    }

    @Test
    public void getCountTest() throws DaoException {
        int id = 1;
        long expectedResult = 2;
        String name = "test";
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Checklist.getCount")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedResult);

        assertThat(checklistDao.getCount(id, name), is(expectedResult));
    }

    @Test(expected = DaoException.class)
    public void getCountExceptionTest() throws DaoException {
        int id = 1;
        String name = "test";

        doThrow(Exception.class).when(mockEm).createNamedQuery("Checklist.getCount");
        checklistDao.getCount(id, name);
    }

    @Test
    public void updateTest() throws DaoException {
        Checklist checklist = new Checklist();
        checklistDao.update(checklist);

        verify(mockEm).merge(checklist);
    }

    @Test(expected = DaoException.class)
    public void updateExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).merge(any());
        checklistDao.update(new Checklist());
    }

    @Test
    public void deleteTest() throws DaoException {
        int id = 1;
        Checklist checklist = mock(Checklist.class);

        doReturn(checklist).when(spyChecklistDao).get(id);
        spyChecklistDao.delete(id);

        verify(mockEm).remove(checklist);
    }

    @Test(expected = DaoException.class)
    public void deleteExceptionTest() throws DaoException {
        int id = 1;
        Checklist checklist = mock(Checklist.class);

        doReturn(checklist).when(spyChecklistDao).get(id);
        doThrow(Exception.class).when(mockEm).remove(any());
        spyChecklistDao.delete(id);
    }
}
