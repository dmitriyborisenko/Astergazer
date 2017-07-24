package ua.dborisenko.astergazer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.exception.BlockNotFoundException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.ITranslatorService;

@RestController
public class TranslatorRestController {

    private static final String RECORD_NOT_FOUND_ERROR = "; ERROR 404: record not found\n";
    private static final String BLOCK_NOT_FOUND_ERROR = "; ERROR 500: block not found\n";
    private static final String INTERNAL_ERROR = "; ERROR 500: Internal service error\n";

    private static final Logger log = LoggerFactory.getLogger(TranslatorRestController.class);

    @Autowired
    private ITranslatorService translatorService;

    @RequestMapping(value = "/translator/{scriptId}", produces = "text/plain; charset=utf-8")
    public String translateScript(@PathVariable int scriptId) throws ServiceException {
        return translatorService.getTranslatedScript(scriptId);
    }

    @RequestMapping(value = "/translator", produces = "text/plain; charset=utf-8")
    public String translateDialplan() {
        return translatorService.getTranslatedDialplan();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public String handleRecordNotFoundException(HttpServletRequest request, Exception e) {
        log.warn("Record not found for request {}", request.getRequestURL(), e);
        return RECORD_NOT_FOUND_ERROR;
    }

    @ExceptionHandler(BlockNotFoundException.class)
    public String handleBlockNotFoundException(HttpServletRequest request, Exception e) {
        log.error("Block not found for request {}", request.getRequestURL(), e);
        return BLOCK_NOT_FOUND_ERROR;
    }

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(HttpServletRequest request, Exception e) {
        log.error("Service error during request execution {}", request.getRequestURL(), e);
        return INTERNAL_ERROR;
    }
}
