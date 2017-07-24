package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Background")
public class BackgroundBlock extends Block {

    public BackgroundBlock() {
        application = "Background";
        parametersCount = 4;
    }
}
