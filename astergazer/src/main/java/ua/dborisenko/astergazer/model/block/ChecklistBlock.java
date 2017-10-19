package ua.dborisenko.astergazer.model.block;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Checklist")
public class ChecklistBlock extends Block {

    public ChecklistBlock() {
        this.isSwitcher = true;
        this.isAgiComplexBlock = true;
    }

    @Override
    public String translate(List<Block> trueCaseBlocks, String fastAgiHost) {
        String implodedParameters = "agi://" +
                fastAgiHost +
                ":4573/checklist.agi?listName=" +
                getParameters().get(0).getValue() +
                "&expression=" +
                getParameters().get(1).getValue() +
                "&trueCaseLabel=Case_" +
                trueCaseBlocks.get(0).getLocalId();
        return buildCommandString(getLabel(), "AGI", implodedParameters);
    }
}
