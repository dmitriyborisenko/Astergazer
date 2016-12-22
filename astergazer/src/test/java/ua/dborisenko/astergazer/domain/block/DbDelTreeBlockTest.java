package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DbDelTreeBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "family", "keytree" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),DBdeltree(family/keytree)\n";
        DbDelTreeBlock block = new DbDelTreeBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
    
    @Test
    public void translateOnlyFamilyTest() {
        String caption = "caption";
        String[] parameters = { "family" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),DBdeltree(family)\n";
        DbDelTreeBlock block = new DbDelTreeBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult));
    }
}
