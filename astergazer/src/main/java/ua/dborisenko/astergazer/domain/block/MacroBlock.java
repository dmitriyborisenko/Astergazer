package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Macro")
public class MacroBlock extends Block {

    public MacroBlock() {
        application = "Macro";
        parametersCount = 2;
    }
}
