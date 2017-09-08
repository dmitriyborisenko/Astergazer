package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Custom")
public class CustomBlock extends Block {

    @Override
    public String translate() {
        StringBuilder result = new StringBuilder();
        int paramIndex = 0;
        for (BlockParameter parameter : getParameters()) {
            if (!"".equals(parameter.getValue())) {
                if (paramIndex == 0) {
                    result.append(buildCommandString(getLabel(), parameter.getValue()));
                } else {
                    result.append(buildCommandString(getLabel() + "_" + paramIndex, parameter.getValue()));
                }
                paramIndex++;
            }
        }
        return result.toString();
    }
}
