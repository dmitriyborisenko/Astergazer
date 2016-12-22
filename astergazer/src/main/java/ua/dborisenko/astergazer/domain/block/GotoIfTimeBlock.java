package ua.dborisenko.astergazer.domain.block;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GotoIfTime")
public class GotoIfTimeBlock extends Block {

    public GotoIfTimeBlock() {
        this.isSwitcher = true;
    }

    @Override
    public String translate(List<Block> trueCaseBlocks) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= 3; i++) {
            if ("".equals(getParameters().get(i).getValue())) {
                result.append("*");
            } else {
                result.append(getParameters().get(i).getValue());
            }
            result.append(",");
        }
        if (!"".equals(getParameters().get(4).getValue())) {
            result.append(getParameters().get(4).getValue());
        }
        result.append("?Case_");
        result.append(trueCaseBlocks.get(0).getLocalId());
        return buildCommandString(getLabel(), "GotoIfTime", result.toString());
    }
}
