package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SetGlobal")
public class SetGlobalBlock extends Block {

    @Override
    public String translate() {
        StringBuilder result = new StringBuilder("GLOBAL(");
        result.append(getParameters().get(0).getValue());
        result.append(")=");
        result.append(getParameters().get(1).getValue());
        return buildCommandString(getLabel(), "Set", result.toString());
    }
}
