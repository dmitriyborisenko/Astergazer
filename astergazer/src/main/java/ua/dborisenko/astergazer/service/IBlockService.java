package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.exception.ServiceException;

public interface IBlockService {
    
    public void addStartBlockToScript(Script script) throws ServiceException;
}
