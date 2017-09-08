package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SayNumber")
public class SayNumberBlock extends Block {

    public SayNumberBlock() {
        application = "SayNumber";
        parametersCount = 2;
    }
}
