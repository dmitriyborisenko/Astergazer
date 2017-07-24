package ua.dborisenko.astergazer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.domain.RestResult;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IExtensionService;
import ua.dborisenko.astergazer.service.IScriptService;

@RestController
@RequestMapping(value = "/mapping/rest")
public class MappingRestController {

    private static final Logger log = LoggerFactory.getLogger(MappingRestController.class);

    @Autowired
    private IScriptService scriptService;

    @Autowired
    private IContextService contextService;

    @Autowired
    private IExtensionService extensionService;

    @RequestMapping(value = "/getscripts")
    public RestResult getScripts() throws ServiceException {
        RestResult result = new RestResult();
        result.addToData("scriptList", scriptService.getScriptsDto());
        return result;
    }

    @RequestMapping(value = "/addscript", method = RequestMethod.POST)
    public RestResult addScript(@RequestParam String name) throws ServiceException {
        RestResult result = new RestResult();
        scriptService.create(name);
        return result;
    }

    @RequestMapping(value = "/updatescript/{id}", method = RequestMethod.POST)
    public RestResult updateScript(@PathVariable int id, @RequestParam String name)
            throws ServiceException {
        RestResult result = new RestResult();
        scriptService.update(id, name);
        return result;
    }

    @RequestMapping(value = "/deletescript/{id}", method = RequestMethod.POST)
    public RestResult deleteScript(@PathVariable int id) throws ServiceException {
        RestResult result = new RestResult();
        scriptService.delete(id);
        return result;
    }

    @RequestMapping(value = "/addcontext", method = RequestMethod.POST)
    public RestResult addContext(@RequestParam String name) throws ServiceException {
        RestResult result = new RestResult();
        contextService.create(name);
        return result;
    }

    @RequestMapping(value = "/updatecontext/{id}", method = RequestMethod.POST)
    public RestResult updateContext(@PathVariable int id, @RequestParam String name)
            throws ServiceException {
        RestResult result = new RestResult();
        contextService.update(id, name);
        return result;
    }

    @RequestMapping(value = "/deletecontext/{id}", method = RequestMethod.POST)
    public RestResult deleteContext(@PathVariable int id) throws ServiceException {
        RestResult result = new RestResult();
        contextService.delete(id);
        return result;
    }

    @RequestMapping(value = "/addextension", method = RequestMethod.POST)
    public RestResult addExtension(@RequestParam String name, @RequestParam int contextId, @RequestParam int scriptId)
            throws ServiceException {
        RestResult result = new RestResult();
        extensionService.create(name, contextId, scriptId);
        return result;
    }

    @RequestMapping(value = "/updateextension/{id}", method = RequestMethod.POST)
    public RestResult updateExtension(@PathVariable int id, @RequestParam String name, @RequestParam int scriptId)
            throws ServiceException {
        RestResult result = new RestResult();
        extensionService.update(id, scriptId, name);
        return result;
    }

    @RequestMapping(value = "/deleteextension/{id}", method = RequestMethod.POST)
    public RestResult deleteExtension(@PathVariable int id) throws ServiceException {
        RestResult result = new RestResult();
        extensionService.delete(id);
        return result;
    }

    @ExceptionHandler(DuplicatedValueException.class)
    public RestResult handleDuplicatedValueException(HttpServletRequest request, Exception e) {
        log.warn("Duplicated value error during request execution {}", request.getRequestURL());
        log.debug("", e);
        RestResult result = new RestResult();
        result.setCode(400);
        result.setStatus("Bad Request");
        result.addToData("description", "Duplicated value");
        return result;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public RestResult handleRecordNotFoundException(HttpServletRequest request, Exception e) {
        log.warn("Record not found for request {}", request.getRequestURL(), e);
        RestResult result = new RestResult();
        result.setCode(404);
        result.setStatus("Not Found");
        result.addToData("description", "Record not found");
        return result;
    }

    @ExceptionHandler(ServiceException.class)
    public RestResult handleServiceException(HttpServletRequest request, Exception e) {
        log.error("Service error during request execution {}", request.getRequestURL(), e);
        RestResult result = new RestResult();
        result.setCode(500);
        result.setStatus("Internal Server Error");
        result.addToData("description", "Error during request execution");
        return result;
    }
}
