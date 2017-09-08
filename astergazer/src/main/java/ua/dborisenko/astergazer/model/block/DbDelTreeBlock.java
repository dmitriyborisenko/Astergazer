package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DbDelTree")
public class DbDelTreeBlock extends Block {

    @Override
    public String translate() {
        StringBuilder parameterString = new StringBuilder(getParameters().get(0).getValue());
        if (getParameters().size() > 1) {
            if (!"".equals(getParameters().get(1).getValue())) {
                parameterString.append("/");
                parameterString.append(getParameters().get(1).getValue());
            }
        }
        return buildCommandString(getLabel(), "DBdeltree", parameterString.toString());
    }
}
