package ua.dborisenko.astergazer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IScriptService;

@RestController
@RequestMapping(value = "/mapping/tree")
public class MappingTreeController {

    @Autowired
    private IScriptService scriptService;

    @Autowired
    private IContextService contextService;

    @GetMapping(value = "/getscripts")
    public List<JsTreeNodeDto> getScripts() throws ServiceException {
        return scriptService.getScriptsTreeDto();
    }

    @GetMapping(value = "/getcontexts")
    public List<JsTreeNodeDto> getDialplanTree() throws ServiceException {
        return contextService.getContextsTreeDto();
    }

}
