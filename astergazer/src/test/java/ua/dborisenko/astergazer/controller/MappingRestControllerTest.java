package ua.dborisenko.astergazer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.dto.ScriptDto;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IExtensionService;
import ua.dborisenko.astergazer.service.IScriptService;

@RunWith(MockitoJUnitRunner.class)
public class MappingRestControllerTest {

    private static final String CONTROLLER_PATH = "/mapping/rest";

    @InjectMocks
    private MappingRestController controller;

    @Mock
    private IContextService mockContextService;

    @Mock
    private IExtensionService mockExtensionService;

    @Mock
    private IScriptService mockScriptService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void testGetScripts() throws Exception {
        List<ScriptDto> dtoList = new ArrayList<>();
        Script script = new Script();
        script.setId(1);
        dtoList.add(new ScriptDto(script));

        when(mockScriptService.getScriptsDto()).thenReturn(dtoList);

        mockMvc.perform(get(CONTROLLER_PATH + "/getscripts")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.scriptList[0].id", is(1)));
        verify(mockScriptService).getScriptsDto();
    }

    @Test
    public void testAddScript() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addscript").param("name", "testName")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockScriptService).create("testName");
    }

    @Test
    public void testUpdateScript() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updatescript/1").param("name", "testName")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockScriptService).update(1, "testName");
    }

    @Test
    public void testDeleteScript() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deletescript/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockScriptService).delete(1);
    }

    @Test
    public void testAddContext() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addcontext").param("name", "testName")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockContextService).create("testName");
    }

    @Test
    public void testUpdateContext() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updatecontext/1").param("name", "testName")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockContextService).update(1, "testName");
    }

    @Test
    public void testDeleteContext() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deletecontext/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockContextService).delete(1);
    }

    @Test
    public void testAddExtension() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addextension").param("name", "testName").param("scriptId", "2")
                .param("contextId", "3")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockExtensionService).create("testName", 3, 2);
    }

    @Test
    public void testUpdateExtension() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updateextension/1").param("name", "testName").param("scriptId", "2"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockExtensionService).update(1, 2, "testName");
    }

    @Test
    public void testDeleteExtension() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deleteextension/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockExtensionService).delete(1);
    }

}