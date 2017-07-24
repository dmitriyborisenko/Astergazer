package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Return")
public class ReturnBlock extends Block {

    public ReturnBlock() {
        application = "Return";
        parametersCount = 1;
    }
}
