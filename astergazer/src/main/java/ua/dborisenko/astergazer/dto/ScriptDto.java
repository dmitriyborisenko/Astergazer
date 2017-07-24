package ua.dborisenko.astergazer.dto;

import ua.dborisenko.astergazer.domain.Script;

public class ScriptDto {

    private int id;
    private String name;

    public ScriptDto(Script script) {
        this.id = script.getId();
        this.name = script.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}