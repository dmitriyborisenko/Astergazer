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

import ua.dborisenko.astergazer.model.Context;
import ua.dborisenko.astergazer.model.Extension;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionDaoTest {

    private static final Long TEST_ID = 1L;

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private ExtensionDao extensionDao;

    @InjectMocks
    @Spy
    private ExtensionDao spyExtensionDao;

    @Test
    public void getTest() throws DaoException {
        Extension expectedExtension = new Extension();
        expectedExtension.setId(TEST_ID);
        when(mockEm.find(Extension.class, TEST_ID)).thenReturn(expectedExtension);

        assertThat(extensionDao.get(TEST_ID), is(expectedExtension));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = DaoException.class)
    public void getExceptionTest() throws DaoException {
        when(mockEm.find(Extension.class, TEST_ID)).thenThrow(Exception.class);

        extensionDao.get(TEST_ID);
    }

    @Test(expected = RecordNotFoundException.class)
    public void getNotFoundTest() throws DaoException {
        when(mockEm.find(Context.class, TEST_ID)).thenReturn(null);

        extensionDao.get(TEST_ID);
    }

    @Test
    public void addTest() throws DaoException {
        Extension extension = new Extension();

        extensionDao.add(extension);

        verify(mockEm).persist(extension);
    }

    @Test(expected = DaoException.class)
    public void addExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).persist(any());

        extensionDao.add(new Extension());
    }

    @Test
    public void getCountTest() throws DaoException {
        Long contextId = 2L;
        String name = "test";
        long expectedResult = 42;
        Query mockQuery = mock(Query.class);
        when(mockEm.createNamedQuery("Extension.getCount")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedResult);

        assertThat(extensionDao.getCount(TEST_ID, contextId, name), is(expectedResult));
    }

    @Test(expected = DaoException.class)
    public void getCountExceptionTest() throws DaoException {
        Long contextId = 2L;
        String name = "test";
        doThrow(Exception.class).when(mockEm).createNamedQuery("Extension.getCount");

        extensionDao.getCount(TEST_ID, contextId, name);
    }

    @Test
    public void updateTest() throws DaoException {
        Extension extension = new Extension();

        extensionDao.update(extension);

        verify(mockEm).merge(extension);
    }

    @Test(expected = DaoException.class)
    public void updateExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).merge(any());

        extensionDao.update(new Extension());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void deleteTest() throws DaoException {
        Extension extension = mock(Extension.class);
        Context context = mock(Context.class);
        List<Extension> extensionList = mock(ArrayList.class);
        when(extension.getContext()).thenReturn(context);
        when(context.getExtensions()).thenReturn(extensionList);
        doReturn(extension).when(spyExtensionDao).get(TEST_ID);

        spyExtensionDao.delete(TEST_ID);

        verify(extensionList).remove(extension);
        verify(mockEm).remove(extension);
    }

    @Test(expected = DaoException.class)
    public void deleteExceptionTest() throws DaoException {
        Extension extension = mock(Extension.class);
        doReturn(extension).when(spyExtensionDao).get(TEST_ID);
        doThrow(Exception.class).when(mockEm).remove(any());

        spyExtensionDao.delete(TEST_ID);
    }

    @Test
    public void unlinkAllFromScriptTest() throws DaoException {
        Query mockQuery = mock(Query.class);
        when(mockEm.createNamedQuery("Extension.unlinkAllFromScript")).thenReturn(mockQuery);

        extensionDao.unlinkAllExtensionsFromScript(TEST_ID);

        verify(mockQuery).executeUpdate();
    }
}
