package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GotoBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "context", "extension", "priority" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),Goto(context,extension,priority)\n";
        GotoBlock block = new GotoBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
    
