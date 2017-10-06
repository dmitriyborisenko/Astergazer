package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RemoveQueueMemberBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "queuename", "interface" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),RemoveQueueMember(queuename,interface)\n";
        RemoveQueueMemberBlock block = new RemoveQueueMemberBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
