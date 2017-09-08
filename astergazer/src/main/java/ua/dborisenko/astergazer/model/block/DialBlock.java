package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Dial")
public class DialBlock extends Block {

    public DialBlock() {
        application = "Dial";
        parametersCount = 4;
    }
}
