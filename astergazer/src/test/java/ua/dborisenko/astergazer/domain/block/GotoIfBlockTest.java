package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GotoIfBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        int trueCaseLocalId = 42;
        String[] parameters = { "expression" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),GotoIf($[expression]?Case_" + trueCaseLocalId + ")\n";
        GotoIfBlock block = new GotoIfBlock();
        TrueCaseBlock trueCaseBlock = new TrueCaseBlock();
        trueCaseBlock.setLocalId(trueCaseLocalId);
        List<Block> trueCaseBlocks = new ArrayList<>();
        trueCaseBlocks.add(trueCaseBlock);
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(trueCaseBlocks), is(expectedResult));
    }
}
