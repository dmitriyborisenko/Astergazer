package ua.dborisenko.astergazer.dto;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.domain.block.VoiceMenuBlock;

@Component
public class VoiceMenuDtoFactory {
    
    public VoiceMenuDto getDto(VoiceMenuBlock block, List<Block> caseBlocks) {
        VoiceMenuDto dto = new VoiceMenuDto();
        dto.setMenuPrompt(block.getParameters().get(0).getValue());
        dto.setInvalidPrompt(block.getParameters().get(1).getValue());
        dto.setTimeoutPrompt(block.getParameters().get(2).getValue());
        String stringValue = block.getParameters().get(3).getValue();
        if (NumberUtils.isCreatable(stringValue)) {
            dto.setTimeout(Integer.valueOf(stringValue));
        }
        stringValue = block.getParameters().get(4).getValue();
        if (NumberUtils.isCreatable(stringValue)) {
            dto.setTimeoutAttemptsCount(Integer.valueOf(stringValue));
        }
        stringValue = block.getParameters().get(5).getValue();
        if (NumberUtils.isCreatable(stringValue)) {
            dto.setInvalidAttemptsCount(Integer.valueOf(stringValue));
        }
        for (Block caseBlock : caseBlocks) {
            dto.addCaseLabel(caseBlock.getCaption(), caseBlock.getLabel());
        }
        return dto;
    }
}
