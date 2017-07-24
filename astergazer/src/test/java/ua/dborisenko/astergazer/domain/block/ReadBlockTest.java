package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReadBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "variable", "filename", "maxDigits", "options", "attempts", "timeout" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),Read(variable,filename,maxDigits,options,attempts,timeout)\n";
        ReadBlock block = new ReadBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
