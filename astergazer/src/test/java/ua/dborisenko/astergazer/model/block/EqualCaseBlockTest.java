package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EqualCaseBlockTest {

    @Test
    public void translateTest() {
        int localId = 42;
        String expectedResult = Block.COMMAND_PREFIX + "EqualCase_" + localId + "),NoOp(EqualCase)\n";
        EqualCaseBlock block = new EqualCaseBlock();
        block.setLocalId(localId);

        assertThat(block.translate(), is(expectedResult));
    }
}
