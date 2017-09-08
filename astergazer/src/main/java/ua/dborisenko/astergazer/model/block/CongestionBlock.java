package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Congestion")
public class CongestionBlock extends Block {

    public CongestionBlock() {
        application = "Congestion";
        parametersCount = 1;
    }
}
