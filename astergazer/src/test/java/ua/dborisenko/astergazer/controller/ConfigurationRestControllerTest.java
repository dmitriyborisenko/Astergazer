package ua.dborisenko.astergazer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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

import ua.dborisenko.astergazer.domain.ConfigurationParameter;
import ua.dborisenko.astergazer.domain.ConfigurationParameter.Name;
import ua.dborisenko.astergazer.service.IConfigurationService;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationRestControllerTest {

    private static final String CONTROLLER_PATH = "/configuration/rest";

    @InjectMocks
    ConfigurationRestController controller;

    @Mock
    IConfigurationService mockConfigurationService;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void testGetAll() throws Exception {
        Set<ConfigurationParameter> parameters = new HashSet<>();
        String expectedValue = "testValue";
        parameters.add(new ConfigurationParameter(Name.FASTAGI_HOST, expectedValue));

        when(mockConfigurationService.getAll()).thenReturn(parameters);

        mockMvc.perform(get(CONTROLLER_PATH + "/getall")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.parameters[0].value", is(expectedValue)));
        verify(mockConfigurationService).getAll();
    }

    @Test
    public void testGetStamp() throws Exception {
        String expectedValue = "testValue";
        ConfigurationParameter parameter = new ConfigurationParameter(Name.MODIFICATION_STAMP, expectedValue);

        when(mockConfigurationService.getModificationStamp()).thenReturn(parameter);

        mockMvc.perform(get(CONTROLLER_PATH + "/getstamp")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.modificationStamp", is(expectedValue)));
        verify(mockConfigurationService).getModificationStamp();
    }

    @Test
    public void testSaveAll() throws Exception {
        Set<ConfigurationParameter> parameters = new HashSet<>();

        mockMvc.perform(post(CONTROLLER_PATH + "/saveall").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(parameters).getBytes())).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockConfigurationService).setAll(anyObject());
    }
}