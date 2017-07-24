package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Read")
public class ReadBlock extends Block {

    public ReadBlock() {
        application = "Read";
        parametersCount = 6;
    }
}
