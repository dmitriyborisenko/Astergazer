package ua.dborisenko.astergazer.model.block;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.dborisenko.astergazer.dto.VoiceMenuDto;
import ua.dborisenko.astergazer.dto.VoiceMenuDtoFactory;

@Entity
@DiscriminatorValue("VoiceMenu")
public class VoiceMenuBlock extends Block {

    public VoiceMenuBlock() {
        this.isSwitcher = true;
        this.isAgiComplexBlock = true;
    }

    @Override
    public String translate(List<Block> trueCaseBlocks, String fastAgiHost) {
        String encodedMenu;
        try {
            VoiceMenuDtoFactory dtoFactory = new VoiceMenuDtoFactory();
            VoiceMenuDto dto = dtoFactory.getDto(this, trueCaseBlocks);
            ObjectMapper mapper = new ObjectMapper();
            String menuJson = mapper.writeValueAsString(dto);
            encodedMenu = URLEncoder.encode(menuJson, "UTF-8");
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not build the voice menu", e);
        }
        String implodedParameters = "agi://" +
                fastAgiHost +
                ":4573/voicemenu.agi?encodedMenu=" +
                encodedMenu;
        return buildCommandString(getLabel(), "AGI", implodedParameters);
    }
}
