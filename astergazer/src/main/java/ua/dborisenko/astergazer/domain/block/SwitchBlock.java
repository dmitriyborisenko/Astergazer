package ua.dborisenko.astergazer.domain.block;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Switch")
public class SwitchBlock extends Block {

    public SwitchBlock() {
        this.isSwitcher = true;
    }

    @Override
    public String translate(List<Block> trueCaseBlocks) {
        String expression = getParameters().get(0).getValue();
        StringBuilder result = new StringBuilder(buildCommandString(getLabel(), "NoOp", "switch"));
        for (Block block : trueCaseBlocks) {
            StringBuilder command = new StringBuilder("$[");
            command.append(expression);
            command.append("=");
            command.append(block.getCaption());
            command.append("]?");
            command.append(block.getLabel());
            result.append(buildCommandString(getLabel() + "_" + block.getLabel(), "GotoIf", command.toString()));
        }
        return result.toString();
    }
}
