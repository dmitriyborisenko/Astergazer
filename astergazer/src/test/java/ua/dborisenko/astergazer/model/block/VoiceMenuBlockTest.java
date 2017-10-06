package ua.dborisenko.astergazer.model.block;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.dborisenko.astergazer.dto.VoiceMenuDto;
import ua.dborisenko.astergazer.dto.VoiceMenuDtoFactory;

public class VoiceMenuBlockTest {

    private BlockTestUtil blockTestUtil = new BlockTestUtil();

    @Test
    public void translateTest() throws JsonProcessingException, UnsupportedEncodingException {
        VoiceMenuBlock block = new VoiceMenuBlock();
        String caption = "caption";
        String[] parameters = { "menuVoicePrompt", "invalidInputVoicePrompt", "timeoutVoicePrompt", "timeout", "timeoutAttempts", "invalidAttempts" };
        blockTestUtil.setBlockParameters(block, caption, parameters);

        TrueCaseBlock trueCaseBlock = new TrueCaseBlock();
        trueCaseBlock.setCaption("test1");
        List<Block> trueCaseBlocks = new ArrayList<>();
        trueCaseBlocks.add(trueCaseBlock);

        String encodedMenu;
        VoiceMenuDtoFactory dtoFactory = new VoiceMenuDtoFactory();
        VoiceMenuDto dto = dtoFactory.getDto(block, trueCaseBlocks);
        ObjectMapper mapper = new ObjectMapper();
        String menuJson = mapper.writeValueAsString(dto);
        encodedMenu = URLEncoder.encode(menuJson, "UTF-8");

        StringBuilder expectedResult = new StringBuilder(Block.COMMAND_PREFIX + caption + "),AGI(agi://${ASTERGAZER_HOST}:4573/voicemenu.agi?encodedMenu=" + encodedMenu + ")\n");

        assertThat(block.translate(trueCaseBlocks), is(expectedResult.toString()));
    }
}
