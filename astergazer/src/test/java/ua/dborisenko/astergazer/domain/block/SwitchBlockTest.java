package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SwitchBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "expression" };
        StringBuilder expectedResult = new StringBuilder(Block.COMMAND_PREFIX + caption + "),NoOp(switch)\n");
        SwitchBlock block = new SwitchBlock();
        List<Block> trueCaseBlocks = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            EqualCaseBlock caseBlock = new EqualCaseBlock();
            caseBlock.setLocalId(i);
            caseBlock.setCaption("caption" + i);
            trueCaseBlocks.add(caseBlock);
            expectedResult.append(Block.COMMAND_PREFIX + caption + "_EqualCase_" + i + "),GotoIf($[expression=caption" + i + "]?EqualCase_" + i + ")\n");
        }
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(trueCaseBlocks), is(expectedResult.toString()));
    }
}
