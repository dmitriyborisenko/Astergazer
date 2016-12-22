package ua.dborisenko.astergazer.domain.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AmdBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();
    
    @Test
    public void translateTest() {
        String caption = "caption";
        String[] parameters = { "initialSilence", "greeting", "afterGreetingSilence", "totalAnalysisTime",
                "minimumWordLength", "betweenWordSilence", "maximumNumberOfWords", "silenceThreshold",
                "maximumWordLength" };
        String expectedResult = Block.COMMAND_PREFIX + caption
                + "),AMD(initialSilence,greeting,afterGreetingSilence,totalAnalysisTime,minimumWordLength,betweenWordSilence,maximumNumberOfWords,silenceThreshold,maximumWordLength)\n";
        AmdBlock block = new AmdBlock();
        blockTestUtil.setBlockParameters(block, caption, parameters);
        
        assertThat(block.translate(), is(expectedResult));
    }
}
