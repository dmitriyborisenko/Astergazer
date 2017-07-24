package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DbSet")
public class DbSetBlock extends Block {

    @Override
    public String translate() {
        StringBuilder result = new StringBuilder("DB(");
        result.append(getParameters().get(0).getValue());
        result.append("/");
        result.append(getParameters().get(1).getValue());
        result.append(")=");
        result.append(getParameters().get(2).getValue());
        return buildCommandString(getLabel(), "Set", result.toString());
    }
}
