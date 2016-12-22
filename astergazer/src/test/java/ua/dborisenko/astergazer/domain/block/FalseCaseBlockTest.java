package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FalseCaseBlockTest {

    @Test
    public void translateTest() {
        int localId = 42;
        String expectedResult = Block.COMMAND_PREFIX + "Case_" + localId + "),NoOp(false)\n";
        FalseCaseBlock block = new FalseCaseBlock();
        block.setLocalId(localId);

        assertThat(block.translate(), is(expectedResult));
    }
}
