package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ChannelRedirectBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "channel", "context", "extension", "priority" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),ChannelRedirect(channel,context,extension,priority)\n";
        ChannelRedirectBlock block = new ChannelRedirectBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
