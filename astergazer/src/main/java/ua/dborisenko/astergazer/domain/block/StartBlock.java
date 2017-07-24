package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Start")
public class StartBlock extends Block {

    @Override
    public String translate() {
        return buildCommandString(getLabel(), "NoOp", "start");
    }
}
