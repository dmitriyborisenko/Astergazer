package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EqualCase")
public class EqualCaseBlock extends Block {
    
    public EqualCaseBlock() {
        this.isCaseBlock = true;
    }
    
    @Override
    public String getLabel() {
        return "EqualCase_" + getLocalId();
    }
    
    @Override
    public String translate() {
        return buildCommandString(getLabel(), "NoOp", "EqualCase");
    }
}
