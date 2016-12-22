package ua.dborisenko.astergazer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IScriptService;

@RestController
@RequestMapping(value = "/mapping/tree")
public class MappingTreeController {

    private static final Logger log = LoggerFactory.getLogger(MappingTreeController.class);

    @Autowired
    private IScriptService scriptService;
    
    @Autowired
    private IContextService contextService;

    @RequestMapping(value = "/getscripts")
    public List<JsTreeNodeDto> getScripts() throws ServiceException {
        return scriptService.getScriptsTreeDto();
    }
    
    @RequestMapping(value = "/getcontexts")
    public List<JsTreeNodeDto> getDialplanTree() throws ServiceException {
        return contextService.getContextsTreeDto();
    }
    
    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        log.error("Service error during request execution {}", request.getRequestURL(), e);
        response.sendError(500);
    }
}
