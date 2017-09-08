package ua.dborisenko.astergazer.model.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ChannelRedirect")
public class ChannelRedirectBlock extends Block {

    public ChannelRedirectBlock() {
        application = "ChannelRedirect";
        parametersCount = 4;
    }
}
