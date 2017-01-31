package ua.dborisenko.astergazer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import ua.dborisenko.astergazer.dao.IBlockDao;
import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.domain.block.StartBlock;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IBlockService;

@Service
@Transactional
public class BlockService implements IBlockService {

    private static final int START_BLOCK_INIT_POSITION_X = 20;
    private static final int START_BLOCK_INIT_POSITION_Y = 20;

    @Autowired
    private IBlockDao blockDao;
    
    @Override
    public void addStartBlockToScript(Script script) throws ServiceException {
        Block startBlock = new StartBlock();
        startBlock.setType("Start");
        startBlock.setCaption("Start");
        startBlock.setPosX(START_BLOCK_INIT_POSITION_X);
        startBlock.setPosY(START_BLOCK_INIT_POSITION_Y);
        startBlock.setIsLocked(true);
        startBlock.setLocalId(1);
        startBlock.setScript(script);
        try {
            blockDao.addBlock(startBlock);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not add the 'Start' block", e);
        }
    }
}
