package ua.dborisenko.astergazer.fastagi;

import java.io.IOException;
import java.net.URLDecoder;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.dborisenko.astergazer.dto.VoiceMenuDto;

public class VoiceMenuAgi extends BaseAgiScript {

    private static final String INPUT_RESULT_VARIABLE_NAME = "ASTERGAZER_VM_LAST_INPUT";

    private static final String INPUT_STATUS_VARIABLE_NAME = "READSTATUS";
    
    private static final Logger log = LoggerFactory.getLogger(VoiceMenuAgi.class);
    
    private ObjectMapper mapper = new ObjectMapper();

    public void service(AgiRequest request, AgiChannel channel) {
        try {
            String encodedMenu = request.getParameter("encodedMenu");
            String menuJson = URLDecoder.decode(encodedMenu, "UTF-8");
            VoiceMenuDto menuDto = mapper.readValue(menuJson, VoiceMenuDto.class);
            StringBuilder readParams = getReadParams(menuDto);
            performReadIteration(readParams.toString(), menuDto.getInvalidAttemptsCount(), menuDto.getTimeoutAttemptsCount(), menuDto);
        } catch (AgiException | IOException e) {
            log.error("Could not handle AGI request", e);
        }
    }

    private void performReadIteration(String readParams, int invalidAttempts, int timeoutAttempts, VoiceMenuDto menuDto) throws AgiException {
        exec("Read", readParams.toString());
        String inputStatus = getVariable(INPUT_STATUS_VARIABLE_NAME);
        String inputResult = getVariable(INPUT_RESULT_VARIABLE_NAME);
        if ("".equals(inputResult)) {
            inputResult = "#";
        }
        if ("TIMEOUT".equals(inputStatus)) {
            handleTimeout(readParams, invalidAttempts, timeoutAttempts, menuDto);
        } else if (!menuDto.getCaseLabels().containsKey(inputResult)) {
            handleInvalidInput(readParams, invalidAttempts, timeoutAttempts, menuDto, inputResult);
        } else {
            verbose("Correct input: " + inputResult, 0);
            exec("Goto", menuDto.getCaseLabels().get(inputResult));
        }
    }

    private void handleInvalidInput(String readParams, int invalidAttempts, int timeoutAttempts,
            VoiceMenuDto menuDto, String inputResult) throws AgiException {
        verbose("Invalid input: " + inputResult, 0);
        if (invalidAttempts > 0) {
            streamFile(menuDto.getInvalidPrompt());
            performReadIteration(readParams, invalidAttempts - 1, timeoutAttempts, menuDto);
        } else {
            verbose("Invalid input fallthrough",0);
        }
    }

    private void handleTimeout(String readParams, int invalidAttempts, int timeoutAttempts,
            VoiceMenuDto menuDto) throws AgiException {
        verbose("Timeout reached", 0);
        if (timeoutAttempts > 0) {
            streamFile(menuDto.getTimeoutPrompt());
            performReadIteration(readParams, invalidAttempts, timeoutAttempts - 1 , menuDto);
        } else {
            verbose("Timeout fallthrough", 0);
        }
    }
    
    private StringBuilder getReadParams(VoiceMenuDto menuDto) {
        StringBuilder readParams = new StringBuilder(INPUT_RESULT_VARIABLE_NAME);
        readParams.append(",");
        readParams.append(menuDto.getMenuPrompt());
        readParams.append(",1,,1,");
        readParams.append(menuDto.getTimeout());
        return readParams;
    }
}