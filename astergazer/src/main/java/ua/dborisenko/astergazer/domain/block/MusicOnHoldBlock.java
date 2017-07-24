package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MusicOnHold")
public class MusicOnHoldBlock extends Block {

    public MusicOnHoldBlock() {
        application = "MusicOnHold";
        parametersCount = 2;
    }
}
