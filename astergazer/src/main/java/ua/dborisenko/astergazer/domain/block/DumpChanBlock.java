package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DumpChan")
public class DumpChanBlock extends Block {

    public DumpChanBlock() {
        application = "DumpChan";
        parametersCount = 1;
    }
}
