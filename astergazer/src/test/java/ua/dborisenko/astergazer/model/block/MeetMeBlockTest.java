package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MeetMeBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "confNumber", "options", "pin" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),MeetMe(confNumber,options,pin)\n";
        MeetMeBlock block = new MeetMeBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
