package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TrueCase")
public class TrueCaseBlock extends Block {
    
    public TrueCaseBlock() {
        this.isCaseBlock = true;
    }
    
    @Override
    public String getLabel() {
        return "Case_" + getLocalId();
    }
    
    @Override
    public String translate() {
        return buildCommandString(getLabel(), "NoOp", "true");
    }
}
