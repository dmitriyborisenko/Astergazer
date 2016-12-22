package ua.dborisenko.astergazer.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.dto.ScriptDataDto;
import ua.dborisenko.astergazer.service.IScriptService;

@RunWith(MockitoJUnitRunner.class)
public class ConstructorRestControllerTest {

    private static final String CONTROLLER_PATH = "/constructor/rest";

    @InjectMocks
    ConstructorRestController controller;

    @Mock
    IScriptService mockScriptService;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void testGetScript() throws Exception {
        Script script = new Script();
        script.setId(1);
        ScriptDataDto expectedDto = new ScriptDataDto(script);

        when(mockScriptService.getScriptDataDto(1)).thenReturn(expectedDto);

        mockMvc.perform(get(CONTROLLER_PATH + "/getscriptdata/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.dto", is(notNullValue())));
        verify(mockScriptService).getScriptDataDto(1);
    }

    @Test
    public void updateScriptData() throws Exception {
        ScriptDataDto expectedDto = new ScriptDataDto();

        mockMvc.perform(post(CONTROLLER_PATH + "/updatescriptdata/1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedDto).getBytes())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockScriptService).updateData(eq(1), anyObject());
    }

    @Test
    public void testGetModificationStamp() throws Exception {
        when(mockScriptService.getModificationStamp(1)).thenReturn("test");

        mockMvc.perform(get(CONTROLLER_PATH + "/getstamp/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.modificationStamp", is("test")));
        verify(mockScriptService).getModificationStamp(1);
    }
}