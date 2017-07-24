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
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ContextDaoTest {

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ContextDao contextDao;

    @InjectMocks
    @Spy
    private ContextDao spyContextDao;

    @Test
    public void getTest() throws DaoException {
        int id = 1;
        Context expectedContext = new Context();
        expectedContext.setId(id);

        when(mockEm.find(Context.class, id)).thenReturn(expectedContext);

        assertThat(contextDao.get(id), is(expectedContext));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Context.class, id)).thenThrow(Exception.class);
        contextDao.get(id);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getNotFoundTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Context.class, id)).thenReturn(null);
        contextDao.get(id);
    }

    @Test
    public void getAllTest() throws DaoException {
        List<Context> expectedList = new ArrayList<>();
        expectedList.add(new Context());
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Context.getAll")).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);

        assertThat(contextDao.getAll(), is(expectedList));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getAllExceptionTest() throws DaoException {
        when(mockEm.createNamedQuery("Context.getAll")).thenThrow(Exception.class);
        contextDao.getAll();
    }

    @Test
    public void addTest() throws DaoException {
        Context context = new Context();
        contextDao.add(context);

        verify(mockEm).persist(context);
    }

    @Test(expected = DaoException.class)
    public void addExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).persist(any());
        contextDao.add(new Context());
    }

    @Test
    public void getCountTest() throws DaoException {
        int id = 1;
        String name = "test";
        long expectedResult = 2;
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Context.getCount")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedResult);

        assertThat(contextDao.getCount(id, name), is(expectedResult));
    }

    @Test(expected = DaoException.class)
    public void getCountExceptionTest() throws DaoException {
        int id = 1;
        String name = "test";

        doThrow(Exception.class).when(mockEm).createNamedQuery("Context.getCount");
        contextDao.getCount(id, name);
    }

    @Test
    public void updateTest() throws DaoException {
        Context context = new Context();
        contextDao.update(context);

        verify(mockEm).merge(context);
    }

    @Test(expected = DaoException.class)
    public void updateExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).merge(any());
        contextDao.update(new Context());
    }

    @Test
    public void deleteTest() throws DaoException {
        int id = 1;
        Context context = mock(Context.class);

        doReturn(context).when(spyContextDao).get(id);
        spyContextDao.delete(id);

        verify(mockEm).remove(context);
    }

    @Test(expected = DaoException.class)
    public void deleteExceptionTest() throws DaoException {
        int id = 1;
        Context context = mock(Context.class);

        doReturn(context).when(spyContextDao).get(id);
        doThrow(Exception.class).when(mockEm).remove(any());
        spyContextDao.delete(id);
    }
}
