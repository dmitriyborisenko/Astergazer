package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NoOp")
public class NoOpBlock extends Block {

    public NoOpBlock() {
        application = "NoOp";
        parametersCount = 1;
    }
}
