package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Wait")
public class WaitBlock extends Block {

    public WaitBlock() {
        application = "Wait";
        parametersCount = 1;
    }
}
