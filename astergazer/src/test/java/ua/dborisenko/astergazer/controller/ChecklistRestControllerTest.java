package ua.dborisenko.astergazer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.dborisenko.astergazer.service.IChecklistEntryService;
import ua.dborisenko.astergazer.service.IChecklistService;

@RunWith(MockitoJUnitRunner.class)
public class ChecklistRestControllerTest {

    private static final String CONTROLLER_PATH = "/checklists/rest";
    private static final Long TEST_ID = 1L;

    @InjectMocks
    private ChecklistRestController controller;

    @Mock
    private IChecklistService mockChecklistService;

    @Mock
    private IChecklistEntryService mockEntryService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void testAddCheckList() throws Exception {
        String name = "test";

        mockMvc.perform(post(CONTROLLER_PATH + "/addchecklist").param("name", name)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockChecklistService).create(name);
    }

    @Test
    public void testUpdateCheckList() throws Exception {
        String name = "test";

        mockMvc.perform(post(CONTROLLER_PATH + "/updatechecklist/" + TEST_ID).param("name", name))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockChecklistService).update(TEST_ID, name);
    }

    @Test
    public void testDeleteCheckList() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deletechecklist/" + TEST_ID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockChecklistService).delete(TEST_ID);
    }

    @Test
    public void testAddEntry() throws Exception {
        String controlValue = "test1";
        String returnValue = "test2";

        mockMvc.perform(post(CONTROLLER_PATH + "/addentry").param("controlValue", controlValue)
                .param("returnValue", returnValue).param("checklistId", String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockEntryService).create(controlValue, returnValue, TEST_ID);
    }

    @Test
    public void testUpdateEntry() throws Exception {
        String controlValue = "test1";
        String returnValue = "test2";

        mockMvc.perform(post(CONTROLLER_PATH + "/updateentry/" + TEST_ID).param("controlValue", controlValue)
                .param("returnValue", returnValue)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockEntryService).update(TEST_ID, controlValue, returnValue);
    }

    @Test
    public void testDeleteEntry() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deleteentry/" + TEST_ID)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockEntryService).delete(TEST_ID);
    }
}