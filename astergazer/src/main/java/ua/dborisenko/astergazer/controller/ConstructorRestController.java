package ua.dborisenko.astergazer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IScriptService;

@RestController
@RequestMapping(value = "/constructor/rest")
public class ConstructorRestController {

    @Autowired
    private IScriptService scriptService;

    @GetMapping(value = "/getscriptdata/{id}")
    public ResponseEntity getScript(@PathVariable Long id) throws ServiceException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dto", scriptService.getScriptDataDto(id));
        return ResponseEntity.ok().body(parameters);
    }

    @PostMapping(value = "/updatescriptdata/{id}")
    public ResponseEntity updateScriptData(@PathVariable Long id, @RequestBody ScriptDataDto dto) throws ServiceException {
        scriptService.updateData(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getstamp/{id}")
    public ResponseEntity getModificationStamp(@PathVariable Long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("modificationStamp", scriptService.getModificationStamp(id));
        return ResponseEntity.ok().body(parameters);
    }

}
