package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Record")
public class RecordBlock extends Block {
    
    public RecordBlock() {
        application = "Record";
        parametersCount = 4;
    }
}
