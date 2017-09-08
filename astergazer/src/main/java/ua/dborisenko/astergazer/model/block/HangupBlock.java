package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Hangup")
public class HangupBlock extends Block {

    public HangupBlock() {
        application = "Hangup";
        parametersCount = 1;
    }
}
