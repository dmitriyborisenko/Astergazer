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
        int id = 1;
        String name = "test";

        mockMvc.perform(post(CONTROLLER_PATH + "/updatechecklist/" + id).param("name", name))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockChecklistService).update(id, name);
    }

    @Test
    public void testDeleteCheckList() throws Exception {
        int id = 1;

        mockMvc.perform(post(CONTROLLER_PATH + "/deletechecklist/" + id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockChecklistService).delete(id);
    }

    @Test
    public void testAddEntry() throws Exception {
        int checklistId = 1;
        String controlValue = "test1";
        String returnValue = "test2";

        mockMvc.perform(post(CONTROLLER_PATH + "/addentry").param("controlValue", controlValue)
                .param("returnValue", returnValue).param("checklistId", String.valueOf(checklistId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockEntryService).create(controlValue, returnValue, checklistId);
    }

    @Test
    public void testUpdateEntry() throws Exception {
        long id = 1;
        String controlValue = "test1";
        String returnValue = "test2";

        mockMvc.perform(post(CONTROLLER_PATH + "/updateentry/" + id).param("controlValue", controlValue)
                .param("returnValue", returnValue)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockEntryService).update(id, controlValue, returnValue);
    }

    @Test
    public void testDeleteEntry() throws Exception {
        long id = 1;

        mockMvc.perform(post(CONTROLLER_PATH + "/deleteentry/" + id)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.code", is(200)));
        verify(mockEntryService).delete(id);
    }
}