package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class QueueBlockTest {
    
    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "queuename", "options", "url", "announceOverride", "timeout", "agi", "macro", "gosub", "rule", "position"};
        String expectedResult = Block.COMMAND_PREFIX + caption + "),Queue(queuename,options,url,announceOverride,timeout,agi,macro,gosub,rule,position)\n";
        QueueBlock block = new QueueBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);
        
        assertThat(block.translate(), is(expectedResult));
    }
}
