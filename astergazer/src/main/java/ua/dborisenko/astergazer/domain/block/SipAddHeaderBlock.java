package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SipAddHeader")
public class SipAddHeaderBlock extends Block {
    
    @Override
    public String translate() {
        StringBuilder result = new StringBuilder(getParameters().get(0).getValue());
        result.append(":");
        result.append(getParameters().get(1).getValue());
        return buildCommandString(getLabel(), "SIPAddHeader", result.toString());
    }
}
