package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Progress")
public class ProgressBlock extends Block {
    
    public ProgressBlock() {
        application = "Progress";
    }
}
