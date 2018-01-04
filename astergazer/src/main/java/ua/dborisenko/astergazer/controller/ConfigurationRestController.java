package ua.dborisenko.astergazer.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dborisenko.astergazer.model.ConfigurationParameter;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IConfigurationService;

@RestController
@RequestMapping(value = "/configuration/rest")
public class ConfigurationRestController {

    @Autowired
    private IConfigurationService configurationService;


    @GetMapping(value = "/getall")
    public ResponseEntity getAll() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parameters", configurationService.getAll());
        return ResponseEntity.ok().body(parameters);
    }

    @GetMapping(value = "/getstamp")
    public ResponseEntity getModificationStamp() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("modificationStamp", configurationService.getModificationStamp().getValue());
        return ResponseEntity.ok().body(parameters);
    }

    @PostMapping(value = "/saveall")
    public ResponseEntity saveAll(@RequestBody Set<ConfigurationParameter> parameters) throws ServiceException {
        configurationService.setAll(parameters);
        return ResponseEntity.ok().build();
    }

}
