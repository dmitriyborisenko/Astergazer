package ua.dborisenko.astergazer.dto;

import java.util.HashMap;
import java.util.Map;


public class VoiceMenuDto {

    private String menuPrompt;

    private String invalidPrompt;

    private String timeoutPrompt;

    private int invalidAttemptsCount;

    private int timeoutAttemptsCount;

    private int timeout;

    private Map<String, String> caseLabels = new HashMap<>();

    public VoiceMenuDto() {}
    
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
    
    public void addCaseLabel(String caption, String label) {
        this.caseLabels.put(caption, label);
    }
    
}
