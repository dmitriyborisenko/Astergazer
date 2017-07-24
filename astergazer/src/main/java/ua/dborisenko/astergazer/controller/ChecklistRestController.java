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
import ua.dborisenko.astergazer.service.IChecklistEntryService;
import ua.dborisenko.astergazer.service.IChecklistService;

@RestController
@RequestMapping(value = "/checklists/rest")
public class ChecklistRestController {

    private static final Logger log = LoggerFactory.getLogger(ChecklistRestController.class);

    @Autowired
    private IChecklistService checklistService;

    @Autowired
    private IChecklistEntryService entryService;

    @RequestMapping(value = "/addchecklist", method = RequestMethod.POST)
    public RestResult addChecklist(@RequestParam String name) throws ServiceException {
        RestResult result = new RestResult();
        checklistService.create(name);
        return result;
    }

    @RequestMapping(value = "/updatechecklist/{id}", method = RequestMethod.POST)
    public RestResult updateChecklist(@PathVariable int id, @RequestParam String name) throws ServiceException {
        RestResult result = new RestResult();
        checklistService.update(id, name);
        return result;
    }

    @RequestMapping(value = "/deletechecklist/{id}", method = RequestMethod.POST)
    public RestResult deleteChecklist(@PathVariable int id) throws ServiceException {
        RestResult result = new RestResult();
        checklistService.delete(id);
        return result;
    }

    @RequestMapping(value = "/addentry", method = RequestMethod.POST)
    public RestResult addEntry(@RequestParam String controlValue, @RequestParam String returnValue, @RequestParam int checklistId)
            throws DuplicatedValueException, ServiceException {
        RestResult result = new RestResult();
        entryService.create(controlValue, returnValue, checklistId);
        return result;
    }

    @RequestMapping(value = "/updateentry/{id}", method = RequestMethod.POST)
    public RestResult updateEntry(@PathVariable long id, @RequestParam String controlValue, @RequestParam String returnValue)
            throws ServiceException {
        RestResult result = new RestResult();
        entryService.update(id, controlValue, returnValue);
        return result;
    }

    @RequestMapping(value = "/deleteentry/{id}", method = RequestMethod.POST)
    public RestResult deleteEntry(@PathVariable long id) throws ServiceException {
        RestResult result = new RestResult();
        entryService.delete(id);
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
