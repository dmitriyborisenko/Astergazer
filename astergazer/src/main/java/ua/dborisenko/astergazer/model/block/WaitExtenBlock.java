package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WaitExten")
public class WaitExtenBlock extends Block {

    public WaitExtenBlock() {
        application = "WaitExten";
        parametersCount = 2;
    }
}
