package ua.dborisenko.astergazer.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configuration")
public class ConfigurationParameter {
    
    public static enum Name {
        FASTAGI_HOST,
        MODIFICATION_STAMP
    }
    
    public static enum DefaultValue {
        localhost
    }
    
    public ConfigurationParameter() {}
    
    public ConfigurationParameter(Name name, String value) {
        setName(name);
        setValue(value);
    }
    
    @Id
    private Name name;

    private String value;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
