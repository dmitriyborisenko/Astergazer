package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Gosub")
public class GosubBlock extends Block {

    @Override
    public String translate() {
        StringBuilder parameterString = new StringBuilder(getParameters().get(0).getValue());
        parameterString.append(",");
        parameterString.append(getParameters().get(1).getValue());
        parameterString.append(",");
        parameterString.append(getParameters().get(2).getValue());
        if (getParameters().size() >= 4) {
            if (!"".equals(getParameters().get(3).getValue())) {
                parameterString.append("(");
                parameterString.append(getParameters().get(3).getValue());
                parameterString.append(")");
            }
        }
        return buildCommandString(getLabel(), "Gosub", parameterString.toString());
    }
}
