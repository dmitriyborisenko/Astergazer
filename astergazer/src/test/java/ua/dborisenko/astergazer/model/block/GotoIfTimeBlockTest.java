package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GotoIfTimeBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() {
        String caption = "caption";
        int trueCaseLocalId = 42;
        String[] parameters = { "timeInterval", "weekDays", "monthDays", "months", "timeZone" };
        String expectedResult = Block.COMMAND_PREFIX + caption + "),GotoIfTime(timeInterval,weekDays,monthDays,months,timeZone?Case_" + trueCaseLocalId + ")\n";
        GotoIfTimeBlock block = new GotoIfTimeBlock();
        TrueCaseBlock trueCaseBlock = new TrueCaseBlock();
        trueCaseBlock.setLocalId(trueCaseLocalId);
        List<Block> trueCaseBlocks = new ArrayList<>();
        trueCaseBlocks.add(trueCaseBlock);
        blockTestUtil.setBlockParameters(block, caption, parameters);

        assertThat(block.translate(trueCaseBlocks), is(expectedResult));
    }
}
