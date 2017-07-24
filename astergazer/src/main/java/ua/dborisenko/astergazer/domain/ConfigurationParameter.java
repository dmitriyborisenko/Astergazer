package ua.dborisenko.astergazer.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "configuration")
public class ConfigurationParameter {

    public enum PARAM_NAME {
        FASTAGI_HOST("localhost"),
        MODIFICATION_STAMP("");

        final private String defaultValue;

        PARAM_NAME(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public ConfigurationParameter() {
    }

    public ConfigurationParameter(PARAM_NAME name, String value) {
        setName(name);
        setValue(value);
    }

    @Id
    private PARAM_NAME name;

    private String value;

    public PARAM_NAME getName() {
        return name;
    }

    public void setName(PARAM_NAME name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
