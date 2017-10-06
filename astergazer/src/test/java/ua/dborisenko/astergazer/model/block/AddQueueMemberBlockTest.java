package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AddQueueMemberBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "queueName", "interface", "penalty", "options", "memberName", "stateInterface" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),AddQueueMember(queueName,interface,penalty,options,memberName,stateInterface)\n";
        AddQueueMemberBlock block = new AddQueueMemberBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }

}
