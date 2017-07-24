package ua.dborisenko.astergazer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.domain.RestResult;
import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IScriptService;

@RestController
@RequestMapping(value = "/constructor/rest")
public class ConstructorRestController {

    private static final Logger log = LoggerFactory.getLogger(ConstructorRestController.class);

    @Autowired
    private IScriptService scriptService;

    @RequestMapping(value = "/getscriptdata/{id}")
    public RestResult getScript(@PathVariable int id) throws ServiceException {
        RestResult result = new RestResult();
        result.addToData("dto", scriptService.getScriptDataDto(id));
        return result;
    }

    @RequestMapping(value = "/updatescriptdata/{id}", method = RequestMethod.POST)
    public RestResult updateScriptData(@PathVariable int id, @RequestBody ScriptDataDto dto) throws ServiceException {
        RestResult result = new RestResult();
        scriptService.updateData(id, dto);
        return result;
    }

    @RequestMapping(value = "/getstamp/{id}")
    public RestResult getModificationStamp(@PathVariable int id) throws ServiceException {
        RestResult result = new RestResult();
        result.addToData("modificationStamp", scriptService.getModificationStamp(id));
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
        log.warn("Record is not found during request execution {}", request.getRequestURL(), e);
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
