package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CheckListBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String fastAgiHost = "testHost";
        String caption = "caption";
        String[] parameters = { "checklist", "expression" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),AGI(agi://" + fastAgiHost + ":4573/checklist.agi?listName=checklist&expression=expression&trueCaseLabel=Case_0)\n";
        ChecklistBlock block = new ChecklistBlock();
        TrueCaseBlock trueCaseBlock = new TrueCaseBlock();
        List<Block> trueCaseBlocks = new ArrayList<>();
        trueCaseBlocks.add(trueCaseBlock);
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(trueCaseBlocks, fastAgiHost), is(expectedResult));
    }
}
