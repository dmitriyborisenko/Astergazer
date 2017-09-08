package ua.dborisenko.astergazer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IScriptService;

@Controller
@RequestMapping
public class DialplanController {

    private static final Logger log = LoggerFactory.getLogger(DialplanController.class);

    @Autowired
    private IScriptService scriptService;

    @RequestMapping(value = "/settings")
    public ModelAndView showConfiguration() throws ServiceException {
        ModelAndView modelAndView = new ModelAndView("configuration");
        return modelAndView;
    }

    @RequestMapping(value = { "/mapping", "/" })
    public ModelAndView showDialplanMap() throws ServiceException {
        ModelAndView modelAndView = new ModelAndView("mapping");
        return modelAndView;
    }

    @RequestMapping(value = "/constructor/{scriptId}")
    public ModelAndView showConstructor(@PathVariable Long scriptId) throws ServiceException {
        ModelAndView modelAndView = new ModelAndView("constructor");
        modelAndView.addObject("script", scriptService.get(scriptId));
        return modelAndView;
    }

    @RequestMapping(value = "/checklists")
    public ModelAndView showCheckLists() throws ServiceException {
        ModelAndView modelAndView = new ModelAndView("checklists");
        return modelAndView;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ModelAndView handleRecordNotFoundException(HttpServletRequest request, Exception e) {
        log.warn("Record not found for request {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView("error404");
        modelAndView.addObject("errorText", "script.notFound");
        return modelAndView;
    }

    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceException(HttpServletRequest request, Exception e) {
        log.error("Service error during request execution {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView("error500");
        return modelAndView;
    }
}
