package ua.dborisenko.astergazer.model.block;

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
