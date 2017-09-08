package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MeetMe")
public class MeetMeBlock extends Block {

    public MeetMeBlock() {
        application = "MeetMe";
        parametersCount = 3;
    }
}
