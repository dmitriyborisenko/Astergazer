package ua.dborisenko.astergazer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

import ua.dborisenko.astergazer.model.Context;
import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.dto.JsTreeNodeDto;
import ua.dborisenko.astergazer.service.IContextService;
import ua.dborisenko.astergazer.service.IScriptService;

@RunWith(MockitoJUnitRunner.class)
public class MappingTreeControllerTest {

    private static final String CONTROLLER_PATH = "/mapping/tree";

    @InjectMocks
    private MappingTreeController controller;

    @Mock
    private IScriptService mockScriptService;

    @Mock
    private IContextService mockContextService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void testGetScripts() throws Exception {
        List<JsTreeNodeDto> dtoList = new ArrayList<>();
        Script script = new Script();
        script.setId(1L);
        dtoList.add(new JsTreeNodeDto(script));

        when(mockScriptService.getScriptsTreeDto()).thenReturn(dtoList);

        mockMvc.perform(post(CONTROLLER_PATH + "/getscripts")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id", is("script1")));
        verify(mockScriptService).getScriptsTreeDto();
    }

    @Test
    public void testGetContexts() throws Exception {
        List<JsTreeNodeDto> dtoList = new ArrayList<>();
        Context context = new Context();
        context.setId(1L);
        context.setName("testContext");
        dtoList.add(new JsTreeNodeDto(context));

        when(mockContextService.getContextsTreeDto()).thenReturn(dtoList);

        mockMvc.perform(post(CONTROLLER_PATH + "/getcontexts")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id", is("context1")));
        verify(mockContextService).getContextsTreeDto();
    }
}