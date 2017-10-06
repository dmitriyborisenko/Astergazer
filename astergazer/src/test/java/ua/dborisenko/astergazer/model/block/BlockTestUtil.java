package ua.dborisenko.astergazer.model.block;

import java.util.ArrayList;

public class BlockTestUtil {

    void setBlockParameters(Block block, String caption, String[] parameters) {
        block.setCaption(caption);
        block.setParameters(new ArrayList<>());
        for (int i = 0; i < parameters.length; i++) {
            BlockParameter parameter = new BlockParameter();
            parameter.setOrderIndex(i);
            parameter.setValue(parameters[i]);
            block.getParameters().add(parameter);
        }
    }
}
