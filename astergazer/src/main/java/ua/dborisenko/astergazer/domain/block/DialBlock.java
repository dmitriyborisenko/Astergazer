package ua.dborisenko.astergazer.domain.block;

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
