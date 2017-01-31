package ua.dborisenko.astergazer.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.dborisenko.astergazer.service.ITranslatorService;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorRestControllerTest {

    private static final String CONTROLLER_PATH = "/translator";

    @InjectMocks
    TranslatorRestController controller;

    @Mock
    ITranslatorService mockTranslatorService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testTranslateScript() throws Exception {
        String expectedTranslatedScript = "test";

        when(mockTranslatorService.getTranslatedScript(1)).thenReturn(expectedTranslatedScript);

        mockMvc.perform(get(CONTROLLER_PATH + "/1")).andExpect(status().isOk())
                .andExpect(content().contentType("text/plain; charset=utf-8"))
                .andExpect(content().string(expectedTranslatedScript));
        verify(mockTranslatorService).getTranslatedScript(1);
    }

    @Test
    public void testTranslateDialplan() throws Exception {
        String expectedTranslatedDialplan = "test";

        when(mockTranslatorService.getTranslatedDialplan()).thenReturn(expectedTranslatedDialplan);

        mockMvc.perform(get(CONTROLLER_PATH + "/")).andExpect(status().isOk())
                .andExpect(content().contentType("text/plain; charset=utf-8"))
                .andExpect(content().string(expectedTranslatedDialplan));
        verify(mockTranslatorService).getTranslatedDialplan();
    }

}