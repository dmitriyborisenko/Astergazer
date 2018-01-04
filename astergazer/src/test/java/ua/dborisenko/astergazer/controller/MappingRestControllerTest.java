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

import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.dto.ScriptDto;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IExtensionService;
import ua.dborisenko.astergazer.service.IScriptService;

@RunWith(MockitoJUnitRunner.class)
public class MappingRestControllerTest {

    private static final String CONTROLLER_PATH = "/mapping/rest";
    private static final Long TEST_SCRIPT_ID = 1L;
    private static final Long TEST_CONTEXT_ID = 2L;
    private static final Long TEST_EXTENSION_ID = 3L;
    private static final String TEST_NAME = "Foo Bar";
    private MockMvc mockMvc;

    @InjectMocks
    private MappingRestController controller;

    @Mock
    private IContextService mockContextService;

    @Mock
    private IExtensionService mockExtensionService;

    @Mock
    private IScriptService mockScriptService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetScripts() throws Exception {
        List<ScriptDto> dtoList = new ArrayList<>();
        Script script = new Script();
        script.setId(TEST_SCRIPT_ID);
        dtoList.add(new ScriptDto(script));
        when(mockScriptService.getScriptsDto()).thenReturn(dtoList);

        mockMvc.perform(get(CONTROLLER_PATH + "/getscripts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.scriptList[0].id", is(1)));

        verify(mockScriptService).getScriptsDto();
    }

    @Test
    public void testAddScript() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addscript")
                .param("name", TEST_NAME))
                .andExpect(status().isOk());

        verify(mockScriptService).create(TEST_NAME);
    }

    @Test
    public void testUpdateScript() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updatescript/" + TEST_SCRIPT_ID)
                .param("name", TEST_NAME))
                .andExpect(status().isOk());

        verify(mockScriptService).update(TEST_SCRIPT_ID, TEST_NAME);
    }

    @Test
    public void testDeleteScript() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deletescript/" + TEST_SCRIPT_ID))
                .andExpect(status().isOk());

        verify(mockScriptService).delete(TEST_SCRIPT_ID);
    }

    @Test
    public void testAddContext() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addcontext")
                .param("name", TEST_NAME))
                .andExpect(status().isOk());

        verify(mockContextService).create(TEST_NAME);
    }

    @Test
    public void testUpdateContext() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updatecontext/" + TEST_CONTEXT_ID)
                .param("name", TEST_NAME))
                .andExpect(status().isOk());

        verify(mockContextService).update(TEST_CONTEXT_ID, TEST_NAME);
    }

    @Test
    public void testDeleteContext() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deletecontext/" + TEST_CONTEXT_ID))
                .andExpect(status().isOk());

        verify(mockContextService).delete(TEST_CONTEXT_ID);
    }

    @Test
    public void testAddExtension() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addextension")
                .param("name", TEST_NAME)
                .param("scriptId", TEST_SCRIPT_ID.toString())
                .param("contextId", TEST_CONTEXT_ID.toString()))
                .andExpect(status().isOk());

        verify(mockExtensionService).create(TEST_NAME, TEST_CONTEXT_ID, TEST_SCRIPT_ID);
    }

    @Test
    public void testUpdateExtension() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updateextension/" + TEST_EXTENSION_ID)
                .param("name", TEST_NAME)
                .param("scriptId", TEST_SCRIPT_ID.toString()))
                .andExpect(status().isOk());

        verify(mockExtensionService).update(TEST_EXTENSION_ID, TEST_SCRIPT_ID, TEST_NAME);
    }

    @Test
    public void testDeleteExtension() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deleteextension/" + TEST_EXTENSION_ID))
                .andExpect(status().isOk());

        verify(mockExtensionService).delete(TEST_EXTENSION_ID);
    }

}