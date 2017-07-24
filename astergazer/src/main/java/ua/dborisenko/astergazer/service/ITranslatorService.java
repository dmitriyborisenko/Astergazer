package ua.dborisenko.astergazer.service;

import ua.dborisenko.astergazer.exception.ServiceException;

public interface ITranslatorService {

    String getTranslatedScript(int id) throws ServiceException;

    String getTranslatedDialplan();
}
