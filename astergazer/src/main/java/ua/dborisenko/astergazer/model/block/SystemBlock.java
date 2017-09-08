package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("System")
public class SystemBlock extends Block {

    public SystemBlock() {
        application = "System";
        parametersCount = 1;
    }
}
