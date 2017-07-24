package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("StartMusicOnHold")
public class StartMusicOnHoldBlock extends Block {

    public StartMusicOnHoldBlock() {
        application = "StartMusicOnHold";
        parametersCount = 1;
    }
}
