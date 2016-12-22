package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface ITranslatorService {

    public String getTranslatedScript(int id) throws ServiceException;

    public String getTranslatedDialplan();
}
