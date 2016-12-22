package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DbSetBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "family", "key", "value" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),Set(DB(family/key)=value)\n";
        DbSetBlock block = new DbSetBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
