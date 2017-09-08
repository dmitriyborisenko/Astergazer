package ua.dborisenko.astergazer.service.impl;

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

import ua.dborisenko.astergazer.dao.impl.ScriptDao;
import ua.dborisenko.astergazer.model.Connection;
import ua.dborisenko.astergazer.model.Script;
import ua.dborisenko.astergazer.model.block.Block;
import ua.dborisenko.astergazer.model.block.BlockParameter;
import ua.dborisenko.astergazer.model.block.CustomBlock;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.ServiceException;

@RunWith(MockitoJUnitRunner.class)
public class ScriptServiceTest {

    @Mock
    private ScriptDao mockScriptDao;

    @InjectMocks
    private ScriptService scriptService;

    @Test
    public void cloneTest() throws ServiceException, DaoException {
        Long originalScriptId = 42L;
        String newScriptName = "newScript";
        Script originalScript = new Script();
        originalScript.setId(originalScriptId);
        addCustomBlockToScript(originalScript,1, "custom1", "foo1", "bar1");
        addCustomBlockToScript(originalScript,2, "custom2", "foo2", "bar2");
        addConnectionToScript(originalScript, 1, 2);
        when(mockScriptDao.getFull(originalScriptId)).thenReturn(originalScript);

        Script newScript = scriptService.clone(originalScriptId, newScriptName);

        verify(mockScriptDao).add(newScript);
        assertTrue(entityListsAreSimilar(originalScript.getConnections(), newScript.getConnections(), connectionsAreSimilar()));
        assertTrue(entityListsAreSimilar(originalScript.getBlocks(), newScript.getBlocks(), blocksAreSimilar()));
    }

    private void addCustomBlockToScript(Script script, int localId, String caption, String paramValue1, String paramValue2) {
        Block block = new CustomBlock();
        block.setLocalId(localId);
        block.setCaption(caption);
        BlockParameter parameter1 = new BlockParameter();
        parameter1.setOrderIndex(1);
        parameter1.setValue(paramValue1);
        parameter1.setBlock(block);
        BlockParameter parameter2 = new BlockParameter();
        parameter2.setOrderIndex(2);
        parameter2.setValue(paramValue2);
        parameter2.setBlock(block);
        block.getParameters().add(parameter1);
        block.getParameters().add(parameter2);
        block.setScript(script);
        script.getBlocks().add(block);
    }

    private void addConnectionToScript(Script script, int sourceBlockLocalId, int targetBlockLocalId) {
        Connection connection = new Connection();
        connection.setSourceBlockLocalId(sourceBlockLocalId);
        connection.setTargetBlockLocalId(targetBlockLocalId);
        connection.setScript(script);
        script.getConnections().add(connection);
    }

    private BiPredicate<Connection, Connection> connectionsAreSimilar() {
        return (connection1, connection2) -> connection1.getIsLocked() == connection2.getIsLocked() &&
                connection1.getSourceBlockLocalId() == connection2.getSourceBlockLocalId() &&
                connection1.getTargetBlockLocalId() == connection2.getTargetBlockLocalId();
    }

    private BiPredicate<Block, Block> blocksAreSimilar() {
        return (block1, block2) -> block1.getCaption().equals(block2.getCaption()) &&
                block1.getClass().equals(block2.getClass()) &&
                block1.getLocalId() == block2.getLocalId() &&
                entityListsAreSimilar(block1.getParameters(), block2.getParameters(), blockParametersAreSimilar());
    }

    private BiPredicate<BlockParameter, BlockParameter> blockParametersAreSimilar() {
        return (parameter1, parameter2) -> parameter1.getOrderIndex() == parameter2.getOrderIndex() &&
                parameter1.getValue().equals(parameter2.getValue());
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
