package ua.dborisenko.astergazer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.dto.JsTreeNodeDynamicDto;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IChecklistService;

@RestController
@RequestMapping(value = "/checklists/tree")
public class ChecklistTreeController {

    @Autowired
    private IChecklistService checklistService;

    @GetMapping(value = "/getchecklists")
    public List<JsTreeNodeDynamicDto> getCheckLists() throws ServiceException {
        return checklistService.getChecklistsTreeDto();
    }

    @GetMapping(value = "/getentries/{checklistId}")
    public List<JsTreeNodeDynamicDto> getEntries(@PathVariable Long checklistId) throws ServiceException {
        return checklistService.getEntriesTreeDto(checklistId);
    }

}
