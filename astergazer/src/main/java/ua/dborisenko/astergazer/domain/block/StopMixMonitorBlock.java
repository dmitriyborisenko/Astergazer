package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("StopMixMonitor")
public class StopMixMonitorBlock extends Block {

    public StopMixMonitorBlock() {
        application = "StopMixMonitor";
        parametersCount = 1;
    }
}
