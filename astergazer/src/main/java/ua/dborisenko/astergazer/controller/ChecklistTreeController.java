package ua.dborisenko.astergazer.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.dto.JsTreeNodeDynamicDto;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IChecklistService;

@RestController
@RequestMapping(value = "/checklists/tree")
public class ChecklistTreeController {

    private static final Logger log = LoggerFactory.getLogger(ChecklistTreeController.class);

    @Autowired
    private IChecklistService checklistService;

    @RequestMapping(value = "/getchecklists")
    public List<JsTreeNodeDynamicDto> getCheckLists() throws ServiceException {
        return checklistService.getChecklistsTreeDto();
    }

    @RequestMapping(value = "/getentries/{checklistId}")
    public List<JsTreeNodeDynamicDto> getEntries(@PathVariable Long checklistId) throws ServiceException {
        return checklistService.getEntriesTreeDto(checklistId);
    }

    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        log.error("Service error during request execution {}", request.getRequestURL(), e);
        response.sendError(500);
    }
}
