package ua.dborisenko.astergazer.dao;

import ua.dborisenko.astergazer.model.block.Block;
import ua.dborisenko.astergazer.exception.DaoException;

public interface IBlockDao {

    void add(Block block) throws DaoException;
}
