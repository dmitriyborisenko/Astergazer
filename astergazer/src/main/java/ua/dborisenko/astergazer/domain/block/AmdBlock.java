package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Amd")
public class AmdBlock extends Block {
    
    public AmdBlock() {
        application = "AMD";
        parametersCount = 9;
    }
}
