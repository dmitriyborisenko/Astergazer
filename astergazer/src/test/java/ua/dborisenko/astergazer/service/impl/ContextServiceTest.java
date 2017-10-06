package ua.dborisenko.astergazer.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.function.BiPredicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dborisenko.astergazer.dao.IContextDao;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.model.Context;
import ua.dborisenko.astergazer.model.Extension;
import ua.dborisenko.astergazer.model.Script;

@RunWith(MockitoJUnitRunner.class)
public class ContextServiceTest {

    @Mock
    private IContextDao mockContextDao;

    @InjectMocks
    private ContextService contextService;

    @Test
    public void cloneTest() throws ServiceException, DaoException {
        Long originalContextId = 42L;
        String newContextName = "newContext";
        Context originalContext = new Context();
        Extension originalExtension = new Extension();
        Script originalScript = new Script();
        originalContext.setId(originalContextId);
        originalExtension.setName("originalExtension");
        originalExtension.setContext(originalContext);
        originalExtension.setScript(originalScript);
        originalContext.getExtensions().add(originalExtension);
        when(mockContextDao.get(originalContextId)).thenReturn(originalContext);

        Context newContext = contextService.clone(originalContextId, newContextName);

        verify(mockContextDao).add(newContext);
        assertTrue(entityListsAreSimilar(originalContext.getExtensions(), newContext.getExtensions(), extensionsAreSimilar()));
        assertThat(newContext.getName(), is(newContextName));
    }

    private BiPredicate<Extension, Extension> extensionsAreSimilar() {
        return (parameter1, parameter2) -> parameter1.getName().equals(parameter2.getName()) &&
                parameter1.getScript() == parameter2.getScript();
    }

    private <T> boolean entityListsAreSimilar(List<T> list1, List<T> list2, BiPredicate<T, T> predicate) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!predicate.test(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

}
