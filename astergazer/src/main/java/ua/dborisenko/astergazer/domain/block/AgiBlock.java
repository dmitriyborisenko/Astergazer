package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Agi")
public class AgiBlock extends Block {
    
    public AgiBlock() {
        application = "AGI";
        parametersCount = 1;
    }
}
