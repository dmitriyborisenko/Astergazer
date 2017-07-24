package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("StopMusicOnHold")
public class StopMusicOnHoldBlock extends Block {

    public StopMusicOnHoldBlock() {
        application = "StopMusicOnHold";
    }
}
