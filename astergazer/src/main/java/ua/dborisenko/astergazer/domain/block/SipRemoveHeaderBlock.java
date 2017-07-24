package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SipRemoveHeader")
public class SipRemoveHeaderBlock extends Block {

    public SipRemoveHeaderBlock() {
        application = "SIPRemoveHeader";
        parametersCount = 1;
    }
}
