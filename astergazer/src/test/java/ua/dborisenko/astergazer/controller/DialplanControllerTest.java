package ua.dborisenko.astergazer.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.service.IScriptService;

@RunWith(MockitoJUnitRunner.class)
public class DialplanControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DialplanController controller;

    @Mock
    private IScriptService mockScriptService;

    @Mock
    private View mockView;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(mockView).build();
    }

    @Test
    public void testShowConfiguration() throws Exception {
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("configuration"));
    }

    @Test
    public void testShowDialplanMap() throws Exception {
        mockMvc.perform(get("/mapping"))
                .andExpect(status().isOk())
                .andExpect(view().name("mapping"));
    }

    @Test
    public void testShowConstructor() throws Exception {
        Script expectedScript = new Script();

        when(mockScriptService.get(anyLong())).thenReturn(expectedScript);

        mockMvc.perform(get("/constructor/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("script", expectedScript))
                .andExpect(view().name("constructor"));
    }

    @Test
    public void testCheckLists() throws Exception {
        mockMvc.perform(get("/checklists"))
                .andExpect(status().isOk())
                .andExpect(view().name("checklists"));
    }
}