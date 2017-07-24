package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ChanSpy")
public class ChanSpyBlock extends Block {

    public ChanSpyBlock() {
        application = "ChanSpy";
        parametersCount = 2;
    }
}
