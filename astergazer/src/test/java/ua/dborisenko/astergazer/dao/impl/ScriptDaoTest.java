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

import ua.dborisenko.astergazer.model.Connection;
import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.model.block.Block;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ScriptDaoTest {

    private static final Long TEST_ID = 1L;

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ScriptDao scriptDao;

    @InjectMocks
    @Spy
    private ScriptDao spyScriptDao;

    @Test
    public void getTest() throws DaoException {
        Script expectedScript = new Script();
        expectedScript.setId(TEST_ID);
        when(mockEm.find(Script.class, TEST_ID)).thenReturn(expectedScript);

        assertThat(scriptDao.get(TEST_ID), is(expectedScript));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        when(mockEm.find(Script.class, TEST_ID)).thenThrow(Exception.class);

        scriptDao.get(TEST_ID);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getNotFoundTest() throws DaoException {
        when(mockEm.find(Script.class, TEST_ID)).thenReturn(null);

        scriptDao.get(TEST_ID);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getFullTest() throws DaoException {
        Script expectedScript = mock(Script.class);
        expectedScript.setId(TEST_ID);
        List<Block> blocks = mock(ArrayList.class);
        List<Connection> connections = mock(ArrayList.class);
        when(mockEm.find(Script.class, TEST_ID)).thenReturn(expectedScript);
        when(expectedScript.getBlocks()).thenReturn(blocks);
        when(expectedScript.getConnections()).thenReturn(connections);

        assertThat(scriptDao.getFull(TEST_ID), is(expectedScript));
        verify(blocks).size();
        verify(connections).size();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getFullExceptionTest() throws DaoException {
        when(mockEm.find(Script.class, TEST_ID)).thenThrow(Exception.class);

        scriptDao.getFull(TEST_ID);
    }

    @Test
    public void addTest() throws DaoException {
        Script script = new Script();

        scriptDao.add(script);

        verify(mockEm).persist(script);
    }

    @Test(expected = DaoException.class)
    public void addExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).persist(any());

        scriptDao.add(new Script());
    }

    @Test
    public void getAllTest() throws DaoException {
        List<Script> expectedList = new ArrayList<>();
        expectedList.add(new Script());
        Query mockQuery = mock(Query.class);
        when(mockEm.createNamedQuery("Script.getAll")).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);

        assertThat(scriptDao.getAll(), is(expectedList));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getAllExceptionTest() throws DaoException {
        when(mockEm.createNamedQuery("Script.getAll")).thenThrow(Exception.class);

        scriptDao.getAll();
    }

    @Test
    public void getCountTest() throws DaoException {
        String name = "test";
        long expectedResult = 2;
        Query mockQuery = mock(Query.class);
        when(mockEm.createNamedQuery("Script.getCount")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedResult);

        assertThat(scriptDao.getCount(TEST_ID, name), is(expectedResult));
    }

    @Test(expected = DaoException.class)
    public void getCountExceptionTest() throws DaoException {
        String name = "test";
        doThrow(Exception.class).when(mockEm).createNamedQuery("Script.getCount");

        scriptDao.getCount(TEST_ID, name);
    }

    @Test
    public void updateTest() throws DaoException {
        Script script = new Script();

        scriptDao.update(script);

        verify(mockEm).merge(script);
    }

    @Test(expected = DaoException.class)
    public void updateExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).merge(any());

        scriptDao.update(new Script());
    }

    @Test
    public void deleteTest() throws DaoException {
        Script script = mock(Script.class);
        doReturn(script).when(spyScriptDao).get(TEST_ID);

        spyScriptDao.delete(TEST_ID);

        verify(mockEm).remove(script);
    }

    @Test(expected = DaoException.class)
    public void deleteExceptionTest() throws DaoException {
        Script script = mock(Script.class);
        doReturn(script).when(spyScriptDao).get(TEST_ID);
        doThrow(Exception.class).when(mockEm).remove(any());

        spyScriptDao.delete(TEST_ID);
    }
}
