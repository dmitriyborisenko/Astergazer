package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TrueCaseBlockTest {

    @Test
    public void translateTest() {
        int localId = 42;
        String expectedResult = Block.COMMAND_PREFIX + "Case_" + localId + "),NoOp(true)\n";
        TrueCaseBlock block = new TrueCaseBlock();
        block.setLocalId(localId);

        assertThat(block.translate(), is(expectedResult));
    }
}
