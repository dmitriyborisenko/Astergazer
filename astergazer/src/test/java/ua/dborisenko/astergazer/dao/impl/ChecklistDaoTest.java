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

import ua.dborisenko.astergazer.model.Checklist;
import ua.dborisenko.astergazer.model.ChecklistEntry;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ChecklistDaoTest {

    private static final Long TEST_ID = 1L;

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ChecklistDao checklistDao;

    @InjectMocks
    @Spy
    private ChecklistDao spyChecklistDao;

    @Test
    public void getTest() throws DaoException {
        Checklist expectedCheckList = new Checklist();
        expectedCheckList.setId(TEST_ID);

        when(mockEm.find(Checklist.class, TEST_ID)).thenReturn(expectedCheckList);

        assertThat(checklistDao.get(TEST_ID), is(expectedCheckList));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        when(mockEm.find(Checklist.class, TEST_ID)).thenThrow(Exception.class);
        checklistDao.get(TEST_ID);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getNotFoundTest() throws DaoException {
        when(mockEm.find(Checklist.class, TEST_ID)).thenReturn(null);
        checklistDao.get(TEST_ID);
    }

    @Test
    public void getByNameTest() throws DaoException {
        String name = "test";
        Checklist expectedCheckList = new Checklist();
        expectedCheckList.setId(TEST_ID);
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
        Checklist mockChecklist = mock(Checklist.class);
        List<ChecklistEntry> mockEntryList = mock(ArrayList.class);

        when(mockChecklist.getEntries()).thenReturn(mockEntryList);
        doReturn(mockChecklist).when(spyChecklistDao).get(TEST_ID);
        spyChecklistDao.getFull(TEST_ID);

        verify(spyChecklistDao).get(TEST_ID);
        verify(mockEntryList).size();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getFullExceptionTest() throws DaoException {
        Checklist mockChecklist = mock(Checklist.class);
        List<ChecklistEntry> mockEntryList = mock(ArrayList.class);

        when(mockChecklist.getEntries()).thenReturn(mockEntryList);
        when(mockEntryList.size()).thenThrow(Exception.class);
        doReturn(mockChecklist).when(spyChecklistDao).get(TEST_ID);
        spyChecklistDao.getFull(TEST_ID);
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
        long expectedResult = 2;
        String name = "test";
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Checklist.getCount")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedResult);

        assertThat(checklistDao.getCount(TEST_ID, name), is(expectedResult));
    }

    @Test(expected = DaoException.class)
    public void getCountExceptionTest() throws DaoException {
        String name = "test";

        doThrow(Exception.class).when(mockEm).createNamedQuery("Checklist.getCount");
        checklistDao.getCount(TEST_ID, name);
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
        Checklist checklist = mock(Checklist.class);

        doReturn(checklist).when(spyChecklistDao).get(TEST_ID);
        spyChecklistDao.delete(TEST_ID);

        verify(mockEm).remove(checklist);
    }

    @Test(expected = DaoException.class)
    public void deleteExceptionTest() throws DaoException {
        Checklist checklist = mock(Checklist.class);

        doReturn(checklist).when(spyChecklistDao).get(TEST_ID);
        doThrow(Exception.class).when(mockEm).remove(any());
        spyChecklistDao.delete(TEST_ID);
    }
}
