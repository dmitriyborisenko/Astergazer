package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FalseCase")
public class FalseCaseBlock extends Block {

    @Override
    public String getLabel() {
        return "Case_" + getLocalId();
    }

    @Override
    public String translate() {
        return buildCommandString(getLabel(), "NoOp", "false");
    }
}
