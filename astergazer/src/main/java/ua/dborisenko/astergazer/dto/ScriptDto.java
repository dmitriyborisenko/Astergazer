package ua.dborisenko.astergazer.dto;

import ua.dborisenko.astergazer.model.Script;

public class ScriptDto {

    private Long id;
    private String name;

    public ScriptDto(Script script) {
        this.id = script.getId();
        this.name = script.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}