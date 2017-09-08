package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CustomBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "command0", "command1", "command2", "command3", "command4", "command5", "command6",
                "command7", "command8", "command9", "command10", "command11", "command12", "command13", "command14" };
        StringBuilder expectedResult = new StringBuilder(Block.COMMAND_PREFIX + caption + "),command0\n");
        for (int i = 1; i < 15; i++) {
            expectedResult.append(Block.COMMAND_PREFIX).append(caption).append("_").append(i).append("),command")
                    .append(i).append("\n");
        }
        CustomBlock block = new CustomBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(), is(expectedResult.toString()));
    }
}
