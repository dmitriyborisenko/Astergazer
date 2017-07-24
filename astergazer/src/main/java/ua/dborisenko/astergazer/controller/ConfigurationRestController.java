package ua.dborisenko.astergazer.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.domain.RestResult;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IConfigurationService;

@RestController
@RequestMapping(value = "/configuration/rest")
public class ConfigurationRestController {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationRestController.class);

    @Autowired
    private IConfigurationService configurationService;


    @RequestMapping(value = "/getall")
    public RestResult getAll() throws ServiceException {
        RestResult result = new RestResult();
        result.addToData("parameters", configurationService.getAll());
        return result;
    }

    @RequestMapping(value = "/getstamp")
    public RestResult getModificationStamp() throws ServiceException {
        RestResult result = new RestResult();
        result.addToData("modificationStamp", configurationService.getModificationStamp().getValue());
        return result;
    }

    @RequestMapping(value = "/saveall", method = RequestMethod.POST)
    public RestResult saveAll(@RequestBody Set<ConfigurationParameter> parameters) throws ServiceException {
        RestResult result = new RestResult();
        configurationService.setAll(parameters);
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
