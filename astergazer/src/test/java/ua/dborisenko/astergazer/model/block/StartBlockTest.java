package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class StartBlockTest {

    @Test
    public void translateTest() {
        String caption = "caption";
        String expectedResult = Block.COMMAND_PREFIX + caption + "),NoOp(start)\n";
        StartBlock block = new StartBlock();
        block.setCaption(caption);

        assertThat(block.translate(), is(expectedResult));
    }
}
