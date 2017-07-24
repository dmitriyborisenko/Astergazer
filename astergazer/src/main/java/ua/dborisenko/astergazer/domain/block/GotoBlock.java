package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Goto")
public class GotoBlock extends Block {

    public GotoBlock() {
        application = "Goto";
        parametersCount = 3;
    }
}
