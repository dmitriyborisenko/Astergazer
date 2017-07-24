package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Queue")
public class QueueBlock extends Block {

    public QueueBlock() {
        application = "Queue";
        parametersCount = 10;
    }
}
