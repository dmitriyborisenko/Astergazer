package ua.dborisenko.astergazer.domain.block;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Checklist")
public class ChecklistBlock extends Block {

    public ChecklistBlock() {
        this.isSwitcher = true;
    }

    @Override
    public String translate(List<Block> trueCaseBlocks) {
        StringBuilder result = new StringBuilder("agi://${ASTERGAZER_HOST}:4573/checklist.agi?listName=");
        result.append(getParameters().get(0).getValue());
        result.append("&expression=");
        result.append(getParameters().get(1).getValue());
        result.append("&trueCaseLabel=Case_");
        result.append(trueCaseBlocks.get(0).getLocalId());
        return buildCommandString(getLabel(), "AGI", result.toString());
    }
}
