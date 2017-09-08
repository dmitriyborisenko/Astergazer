package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NoCdr")
public class NoCdrBlock extends Block {

    public NoCdrBlock() {
        application = "NoCDR";
    }
}
