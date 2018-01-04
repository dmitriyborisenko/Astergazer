package ua.dborisenko.astergazer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IExtensionService;
import ua.dborisenko.astergazer.service.IScriptService;

@RestController
@RequestMapping(value = "/mapping/rest")
public class MappingRestController {

    @Autowired
    private IScriptService scriptService;

    @Autowired
    private IContextService contextService;

    @Autowired
    private IExtensionService extensionService;

    @GetMapping(value = "/getscripts")
    public ResponseEntity getScripts() throws ServiceException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("scriptList", scriptService.getScriptsDto());
        return ResponseEntity.ok().body(parameters);
    }

    @PostMapping(value = "/addscript")
    public ResponseEntity addScript(@RequestParam String name) throws ServiceException {
        scriptService.create(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updatescript/{id}")
    public ResponseEntity updateScript(@PathVariable Long id, @RequestParam String name)
            throws ServiceException {
        scriptService.update(id, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/clonescript/{id}")
    public ResponseEntity cloneScript(@PathVariable Long id, @RequestParam String name)
            throws ServiceException {
        scriptService.clone(id, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/deletescript/{id}")
    public ResponseEntity deleteScript(@PathVariable Long id) throws ServiceException {
        scriptService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/addcontext")
    public ResponseEntity addContext(@RequestParam String name) throws ServiceException {
        contextService.create(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updatecontext/{id}")
    public ResponseEntity updateContext(@PathVariable Long id, @RequestParam String name)
            throws ServiceException {
        contextService.update(id, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/clonecontext/{id}")
    public ResponseEntity cloneContext(@PathVariable Long id, @RequestParam String name)
            throws ServiceException {
        contextService.clone(id, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/deletecontext/{id}")
    public ResponseEntity deleteContext(@PathVariable Long id) throws ServiceException {
        contextService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/addextension")
    public ResponseEntity addExtension(@RequestParam String name, @RequestParam Long contextId, @RequestParam Long scriptId)
            throws ServiceException {
        extensionService.create(name, contextId, scriptId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updateextension/{id}")
    public ResponseEntity updateExtension(@PathVariable Long id, @RequestParam String name, @RequestParam Long scriptId)
            throws ServiceException {
        extensionService.update(id, scriptId, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/deleteextension/{id}")
    public ResponseEntity deleteExtension(@PathVariable Long id) throws ServiceException {
        extensionService.delete(id);
        return ResponseEntity.ok().build();
    }

}
