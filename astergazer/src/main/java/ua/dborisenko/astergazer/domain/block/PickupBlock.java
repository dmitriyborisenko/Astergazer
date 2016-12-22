package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Pickup")
public class PickupBlock extends Block {
    
    public PickupBlock() {
        application = "Pickup";
        parametersCount = 1;
    }
}
