package ua.dborisenko.astergazer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.exception.DuplicatedValueException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IChecklistEntryService;
import ua.dborisenko.astergazer.service.IChecklistService;

@RestController
@RequestMapping(value = "/checklists/rest")
public class ChecklistRestController {

    @Autowired
    private IChecklistService checklistService;

    @Autowired
    private IChecklistEntryService entryService;

    @PostMapping(value = "/addchecklist")
    public ResponseEntity addChecklist(@RequestParam String name) throws ServiceException {
        checklistService.create(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updatechecklist/{id}")
    public ResponseEntity updateChecklist(@PathVariable Long id, @RequestParam String name) throws ServiceException {
        checklistService.update(id, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/deletechecklist/{id}")
    public ResponseEntity deleteChecklist(@PathVariable Long id) throws ServiceException {
        checklistService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/addentry")
    public ResponseEntity addEntry(@RequestParam String controlValue, @RequestParam String returnValue, @RequestParam Long checklistId)
            throws DuplicatedValueException, ServiceException {
        entryService.create(controlValue, returnValue, checklistId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updateentry/{id}")
    public ResponseEntity updateEntry(@PathVariable Long id, @RequestParam String controlValue, @RequestParam String returnValue)
            throws ServiceException {
        entryService.update(id, controlValue, returnValue);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/deleteentry/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id) throws ServiceException {
        entryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
