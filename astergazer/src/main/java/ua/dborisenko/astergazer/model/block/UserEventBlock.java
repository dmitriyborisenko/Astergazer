package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UserEvent")
public class UserEventBlock extends Block {

    public UserEventBlock() {
        application = "UserEvent";
        parametersCount = 2;
    }
}
