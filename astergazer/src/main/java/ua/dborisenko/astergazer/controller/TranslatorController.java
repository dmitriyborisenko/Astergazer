package ua.dborisenko.astergazer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.ITranslatorService;

@Controller
public class TranslatorController {

    private static final Logger log = LoggerFactory.getLogger(TranslatorController.class);
    private static final String RECORD_NOT_FOUND_ERROR_MESSAGE = "; ERROR 404: record not found\n; ";
    private static final String INTERNAL_ERROR_MESSAGE = "; ERROR 500: Internal service error\n; ";

    @Autowired
    private ITranslatorService translatorService;

    @GetMapping(value = "/translator/{scriptId}", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String translateScript(@PathVariable Long scriptId) throws ServiceException {
        return translatorService.getTranslatedScript(scriptId);
    }

    @GetMapping(value = "/translator", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String translateDialplan() {
        return translatorService.getTranslatedDialplan();
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseBody
    public String handleRecordNotFoundException(HttpServletRequest request, RecordNotFoundException e) {
        log.warn("Record not found for request {}", request.getRequestURL(), e);
        return RECORD_NOT_FOUND_ERROR_MESSAGE + e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(HttpServletRequest request, Exception e) {
        log.error("Error during request execution {}", request.getRequestURL(), e);
        return INTERNAL_ERROR_MESSAGE + e.getMessage();
    }
}
