package ua.dborisenko.astergazer.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ua.dborisenko.astergazer.dao.IBlockDao;
import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.exception.DaoException;

@Repository
public class BlockDao implements IBlockDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addBlock(Block block) throws DaoException {
        try {
            em.persist(block);
        } catch (Exception e) {
            throw new DaoException("Could not add the script block", e);
        }
    }
}
