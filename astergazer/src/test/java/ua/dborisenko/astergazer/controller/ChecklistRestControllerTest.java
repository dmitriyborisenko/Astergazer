package ua.dborisenko.astergazer.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private static final String TEST_NAME = "Foo Bar";
    private static final String TEST_CONTROL_VALUE = "Control value";
    private static final String TEST_RETURN_VALUE = "Return value";

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
        mockMvc.perform(post(CONTROLLER_PATH + "/addchecklist")
                .param("name", TEST_NAME))
                .andExpect(status().isOk());

        verify(mockChecklistService).create(TEST_NAME);
    }

    @Test
    public void testUpdateCheckList() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updatechecklist/" + TEST_ID)
                .param("name", TEST_NAME))
                .andExpect(status().isOk());

        verify(mockChecklistService).update(TEST_ID, TEST_NAME);
    }

    @Test
    public void testDeleteCheckList() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deletechecklist/" + TEST_ID))
                .andExpect(status().isOk());

        verify(mockChecklistService).delete(TEST_ID);
    }

    @Test
    public void testAddEntry() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/addentry")
                .param("controlValue", TEST_CONTROL_VALUE)
                .param("returnValue", TEST_RETURN_VALUE)
                .param("checklistId", String.valueOf(TEST_ID)))
                .andExpect(status().isOk());

        verify(mockEntryService).create(TEST_CONTROL_VALUE, TEST_RETURN_VALUE, TEST_ID);
    }

    @Test
    public void testUpdateEntry() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/updateentry/" + TEST_ID)
                .param("controlValue", TEST_CONTROL_VALUE)
                .param("returnValue", TEST_RETURN_VALUE))
                .andExpect(status().isOk());

        verify(mockEntryService).update(TEST_ID, TEST_CONTROL_VALUE, TEST_RETURN_VALUE);
    }

    @Test
    public void testDeleteEntry() throws Exception {
        mockMvc.perform(post(CONTROLLER_PATH + "/deleteentry/" + TEST_ID))
                .andExpect(status().isOk());

        verify(mockEntryService).delete(TEST_ID);
    }
}