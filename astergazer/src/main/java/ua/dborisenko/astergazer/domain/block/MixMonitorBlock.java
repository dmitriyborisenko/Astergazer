package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MixMonitor")
public class MixMonitorBlock extends Block {
    
    public MixMonitorBlock() {
        application = "MixMonitor";
        parametersCount = 3;
    }
}
