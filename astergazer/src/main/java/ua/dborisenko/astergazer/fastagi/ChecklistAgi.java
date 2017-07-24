package ua.dborisenko.astergazer.fastagi;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.exception.RecordNotFoundException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IChecklistEntryService;
import ua.dborisenko.astergazer.service.IChecklistService;

public class ChecklistAgi extends BaseAgiScript {

    private static final Logger log = LoggerFactory.getLogger(ChecklistAgi.class);

    @Autowired
    private IChecklistService checklistService;

    @Autowired
    private IChecklistEntryService entryService;

    public void service(AgiRequest request, AgiChannel channel) {
        try {
            setVariable("CHECKLIST_RETURN_VALUE", "");
            String checklistName = request.getParameter("listName");
            Checklist checklist;
            try {
                checklist = checklistService.getByName(checklistName);
            } catch (RecordNotFoundException e) {
                log.warn("Trying to check control value for non-existent checklist with name {}", checklistName);
                verbose("Checklist with name " + request.getParameter("listName") + " does not exist", 0);
                return;
            }
            String controlValue = getFullVariable(request.getParameter("expression"));
            String returnValue = entryService.getReturnValue(checklist.getId(), controlValue);
            if (returnValue != null) {
                setVariable("CHECKLIST_RETURN_VALUE", returnValue);
                setPriority(request.getParameter("trueCaseLabel"));
            }
        } catch (ServiceException | AgiException e) {
            log.error("Could not handle AGI request", e);
        }
    }
}