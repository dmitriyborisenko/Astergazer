package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IBlockDao {

    void addBlock(Block block) throws DaoException;
}
