package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UserEventBlockTest {
    
    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "name", "body" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),UserEvent(name,body)\n";
        UserEventBlock block = new UserEventBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);
        
        assertThat(block.translate(), is(expectedResult));
    }
}
