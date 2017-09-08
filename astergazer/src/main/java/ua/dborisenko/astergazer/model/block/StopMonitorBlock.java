package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("StopMonitor")
public class StopMonitorBlock extends Block {

    public StopMonitorBlock() {
        application = "StopMonitor";
    }
}
