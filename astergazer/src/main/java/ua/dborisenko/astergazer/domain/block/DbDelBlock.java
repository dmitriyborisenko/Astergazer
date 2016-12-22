package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DbDel")
public class DbDelBlock extends Block {
    
   @Override
   public String translate() {
       StringBuilder parameterString = new StringBuilder(getParameters().get(0).getValue());
       parameterString.append("/");
       parameterString.append(getParameters().get(1).getValue());
       return buildCommandString(getLabel(), "DBdel", parameterString.toString());
   }
}
