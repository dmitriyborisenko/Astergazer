package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RingingBlockTest {

    @Test
    public void translateTest() {
        String caption = "caption";
        String expectedResult = Block.COMMAND_PREFIX + caption + "),Ringing()\n";
        RingingBlock block = new RingingBlock();
        block.setCaption(caption);

        assertThat(block.translate(), is(expectedResult));
    }
}
