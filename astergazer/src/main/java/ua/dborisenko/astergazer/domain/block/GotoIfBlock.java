package ua.dborisenko.astergazer.domain.block;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GotoIf")
public class GotoIfBlock extends Block {

    public GotoIfBlock() {
        this.isSwitcher = true;
    }

    @Override
    public String translate(List<Block> trueCaseBlocks) {
        StringBuilder result = new StringBuilder("$[");
        result.append(getParameters().get(0).getValue());
        result.append("]?Case_");
        result.append(trueCaseBlocks.get(0).getLocalId());
        return buildCommandString(getLabel(), "GotoIf", result.toString());
    }
}
