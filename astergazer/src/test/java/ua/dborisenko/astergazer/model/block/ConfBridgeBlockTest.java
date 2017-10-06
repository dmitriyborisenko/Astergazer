package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConfBridgeBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "conference", "bridgeProfile", "userProfile", "menu" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),ConfBridge(conference,bridgeProfile,userProfile,menu)\n";
        ConfBridgeBlock block = new ConfBridgeBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
