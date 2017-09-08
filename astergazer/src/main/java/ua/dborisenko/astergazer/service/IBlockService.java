package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IBlockService {

    void addStartBlockToScript(Script script) throws ServiceException;
}
