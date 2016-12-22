package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PickupBlockTest {
    
    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "targets" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),Pickup(targets)\n";
        PickupBlock block = new PickupBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);
        
        assertThat(block.translate(), is(expectedResult));
    }
}
