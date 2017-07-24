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

import ua.dborisenko.astergazer.domain.Connection;
import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ScriptDaoTest {

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ScriptDao scriptDao;

    @InjectMocks
    @Spy
    private ScriptDao spyScriptDao;

    @Test
    public void getTest() throws DaoException {
        int id = 1;
        Script expectedScript = new Script();
        expectedScript.setId(id);

        when(mockEm.find(Script.class, id)).thenReturn(expectedScript);

        assertThat(scriptDao.get(id), is(expectedScript));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Script.class, id)).thenThrow(Exception.class);
        scriptDao.get(id);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getNotFoundTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Script.class, id)).thenReturn(null);
        scriptDao.get(id);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getFullTest() throws DaoException {
        int id = 1;
        Script expectedScript = mock(Script.class);
        expectedScript.setId(id);
        List<Block> blocks = mock(ArrayList.class);
        List<Connection> connections = mock(ArrayList.class);

        when(mockEm.find(Script.class, id)).thenReturn(expectedScript);
        when(expectedScript.getBlocks()).thenReturn(blocks);
        when(expectedScript.getConnections()).thenReturn(connections);

        assertThat(scriptDao.getFull(id), is(expectedScript));
        verify(blocks).size();
        verify(connections).size();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getFullExceptionTest() throws DaoException {
        int id = 1;

        when(mockEm.find(Script.class, id)).thenThrow(Exception.class);
        scriptDao.getFull(id);
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
        int id = 1;
        String name = "test";
        long expectedResult = 2;
        Query mockQuery = mock(Query.class);

        when(mockEm.createNamedQuery("Script.getCount")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedResult);

        assertThat(scriptDao.getCount(id, name), is(expectedResult));
    }

    @Test(expected = DaoException.class)
    public void getCountExceptionTest() throws DaoException {
        int id = 1;
        String name = "test";

        doThrow(Exception.class).when(mockEm).createNamedQuery("Script.getCount");
        scriptDao.getCount(id, name);
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
        int id = 1;
        Script script = mock(Script.class);

        doReturn(script).when(spyScriptDao).get(id);
        spyScriptDao.delete(id);

        verify(mockEm).remove(script);
    }

    @Test(expected = DaoException.class)
    public void deleteExceptionTest() throws DaoException {
        int id = 1;
        Script script = mock(Script.class);

        doReturn(script).when(spyScriptDao).get(id);
        doThrow(Exception.class).when(mockEm).remove(any());
        spyScriptDao.delete(id);
    }
}
