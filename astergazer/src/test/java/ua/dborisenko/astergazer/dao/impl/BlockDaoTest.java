package ua.dborisenko.astergazer.dao.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.exception.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class BlockDaoTest {

    @Mock
    private EntityManager mockEm;

    @InjectMocks
    private BlockDao blockDao;

    @Test
    public void addBlockTest() throws DaoException {
        Block block = new Block();
        blockDao.addBlock(block);
        verify(mockEm).persist(block);
    }

    @Test(expected = DaoException.class)
    public void addBlockExceptionTest() throws DaoException {
        doThrow(Exception.class).when(mockEm).persist(any());
        blockDao.addBlock(new Block());
    }
}
