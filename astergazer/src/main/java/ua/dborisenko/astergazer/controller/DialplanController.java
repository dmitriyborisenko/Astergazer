package ua.dborisenko.astergazer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IScriptService;

@Controller
public class DialplanController {

    private static final Logger log = LoggerFactory.getLogger(DialplanController.class);

    @Autowired
    private IScriptService scriptService;

    @GetMapping(value = "/settings")
    public ModelAndView showConfiguration() {
        return new ModelAndView("configuration");
    }

    @GetMapping(value = { "/mapping", "/" })
    public ModelAndView showDialplanMap() {
        return new ModelAndView("mapping");
    }

    @GetMapping(value = "/constructor/{scriptId}")
    public ModelAndView showConstructor(@PathVariable Long scriptId) throws ServiceException {
        return new ModelAndView("constructor")
                .addObject("script", scriptService.get(scriptId));
    }

    @GetMapping(value = "/checklists")
    public ModelAndView showCheckLists() {
        return new ModelAndView("checklists");
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ModelAndView handleRecordNotFoundException(HttpServletRequest request, RecordNotFoundException e) {
        log.warn("Record not found for request {}", request.getRequestURL(), e);
        return new ModelAndView("error404")
                .addObject("errorText", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) {
        log.error("Got error during request execution {}", request.getRequestURL(), e);
        return new ModelAndView("error500")
                .addObject("errorText", e.getMessage());
    }
}
