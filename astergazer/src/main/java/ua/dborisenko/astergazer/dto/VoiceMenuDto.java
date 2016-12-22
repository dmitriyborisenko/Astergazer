package ua.dborisenko.astergazer.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.domain.block.VoiceMenuBlock;


public class VoiceMenuDto {

    private String menuPrompt;

    private String invalidPrompt;

    private String timeoutPrompt;

    private int invalidAttemptsCount;

    private int timeoutAttemptsCount;

    private int timeout;

    private Map<String, String> caseLabels = new HashMap<>();

    public VoiceMenuDto() {}
    
    public VoiceMenuDto(VoiceMenuBlock block, List<Block> caseBlocks) {
        setMenuPrompt(block.getParameters().get(0).getValue());
        setInvalidPrompt(block.getParameters().get(1).getValue());
        setTimeoutPrompt(block.getParameters().get(2).getValue());
        String stringValue = block.getParameters().get(3).getValue();
        if (NumberUtils.isCreatable(stringValue)) {
            setTimeout(Integer.valueOf(stringValue));
        }
        stringValue = block.getParameters().get(4).getValue();
        if (NumberUtils.isCreatable(stringValue)) {
            setTimeoutAttemptsCount(Integer.valueOf(stringValue));
        }
        stringValue = block.getParameters().get(5).getValue();
        if (NumberUtils.isCreatable(stringValue)) {
            setInvalidAttemptsCount(Integer.valueOf(stringValue));
        }
        for (Block caseBlock : caseBlocks) {
            caseLabels.put(caseBlock.getCaption(), caseBlock.getLabel());
        }
    }
    
    public String getMenuPrompt() {
        return menuPrompt;
    }

    public void setMenuPrompt(String menuPrompt) {
        this.menuPrompt = menuPrompt;
    }

    public String getInvalidPrompt() {
        return invalidPrompt;
    }

    public void setInvalidPrompt(String invalidPrompt) {
        this.invalidPrompt = invalidPrompt;
    }

    public String getTimeoutPrompt() {
        return timeoutPrompt;
    }

    public void setTimeoutPrompt(String timeoutPrompt) {
        this.timeoutPrompt = timeoutPrompt;
    }

    public int getInvalidAttemptsCount() {
        return invalidAttemptsCount;
    }

    public void setInvalidAttemptsCount(int invalidAttemptsCount) {
        this.invalidAttemptsCount = invalidAttemptsCount;
    }

    public int getTimeoutAttemptsCount() {
        return timeoutAttemptsCount;
    }

    public void setTimeoutAttemptsCount(int timeoutAttemptsCount) {
        this.timeoutAttemptsCount = timeoutAttemptsCount;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Map<String, String> getCaseLabels() {
        return caseLabels;
    }

    public void setCaseLabels(Map<String, String> labels) {
        this.caseLabels = labels;
    }
    
}
