package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Set")
public class SetBlock extends Block {

    @Override
    public String translate() {
        StringBuilder result = new StringBuilder(getParameters().get(0).getValue());
        result.append("=");
        result.append(getParameters().get(1).getValue());
        return buildCommandString(getLabel(), "Set", result.toString());
    }
}
