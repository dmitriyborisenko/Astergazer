package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Monitor")
public class MonitorBlock extends Block {

    public MonitorBlock() {
        application = "Monitor";
        parametersCount = 3;
    }
}
