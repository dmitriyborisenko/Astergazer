package ua.dborisenko.astergazer.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import ua.dborisenko.astergazer.dao.IContextDao;
import ua.dborisenko.astergazer.dao.IScriptDao;
import ua.dborisenko.astergazer.domain.Connection;
import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.domain.block.Block;
import ua.dborisenko.astergazer.exception.BlockNotFoundException;
import ua.dborisenko.astergazer.exception.DaoException;
import ua.dborisenko.astergazer.exception.ServiceException;
import ua.dborisenko.astergazer.service.IConfigurationService;
import ua.dborisenko.astergazer.service.ITranslatorService;

@Service
public class TranslatorService implements ITranslatorService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorService.class);

    @Autowired
    IConfigurationService configurationService;

    @Autowired
    IScriptDao scriptDao;

    @Autowired
    IContextDao contextDao;

    private String cachedContexts = "";

    private String cachedGlobalVariables = "";

    private Block findStartBlock(Script script) {
        for (Block block : script.getBlocks()) {
            if ("Start".equals(block.getType())) {
                return block;
            }
        }
        throw new BlockNotFoundException("Could not find start block");
    }

    private Block findBlockByLocalId(int localId, Script script) {
        for (Block block : script.getBlocks()) {
            if (block.getLocalId() == localId) {
                return block;
            }
        }
        throw new BlockNotFoundException("Could not find block with local id " + localId);
    }

    private Block findNextBlock(Block block, Script script) {
        for (Connection connection : script.getConnections()) {
            if (connection.getSourceBlockLocalId() == block.getLocalId()) {
                return findBlockByLocalId(connection.getTargetBlockLocalId(), script);
            }
        }
        return null;
    }

    private List<Block> findTrueCaseBlocks(Block switchBlock, Script script, Set<Integer> handledBlocksId) {
        List<Block> result = new ArrayList<>();
        for (Connection connection : script.getConnections()) {
            if (connection.getSourceBlockLocalId() == switchBlock.getLocalId()) {
                Block block = findBlockByLocalId(connection.getTargetBlockLocalId(), script);
                if (block.getIsCaseBlock()) {
                    result.add(block);
                }
            }
        }
        return result;
    }

    private Block findFalseCaseBlock(Block switchBlock, Script script, Set<Integer> handledBlocksId) {
        for (Connection connection : script.getConnections()) {
            if (connection.getSourceBlockLocalId() == switchBlock.getLocalId()) {
                Block block = findBlockByLocalId(connection.getTargetBlockLocalId(), script);
                if ("FalseCase".equals(block.getType())) {
                    return block;
                }
            }
        }
        throw new BlockNotFoundException("Could not find default case block");
    }

    private void handleBranch(StringBuilder result, Script script, Set<Integer> handledBlocksId,
            Deque<Block> rootBlocks) {
        Block block = rootBlocks.getLast();
        rootBlocks.remove(block);
        while (block != null) {
            if (handledBlocksId.contains(block.getLocalId())) {
                result.append("\tsame = n,Goto(" + block.getLabel() + ")\n");
                return;
            }
            if (block.getIsSwitcher()) {
                handleSwitcher(result, script, handledBlocksId, rootBlocks, block);
                return;
            }
            result.append(block.translate());
            handledBlocksId.add(block.getLocalId());
            block = findNextBlock(block, script);
        }
        result.append("\tsame = n,Hangup()\n");

    }

    private void handleSwitcher(StringBuilder result, Script script, Set<Integer> handledBlocksId,
            Deque<Block> rootBlocks, Block block) {
        List<Block> trueCaseBlocks = findTrueCaseBlocks(block, script, handledBlocksId);
        Block falseCaseBlock = findFalseCaseBlock(block, script, handledBlocksId);
        result.append(block.translate(trueCaseBlocks));
        handledBlocksId.add(block.getLocalId());
        rootBlocks.addAll(trueCaseBlocks);
        rootBlocks.addLast(falseCaseBlock);
    }

    private Script loadScript(int id) throws ServiceException {
        try {
            return scriptDao.getFull(id);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not load the script with id " + id, e);
        }
    }

    private String buildScript(Script script) {
        StringBuilder result = new StringBuilder();
        Deque<Block> rootBlocks = new LinkedList<>();
        Set<Integer> handledBlocksId = new HashSet<>();
        rootBlocks.addLast(findStartBlock(script));
        while (rootBlocks.size() > 0) {
            handleBranch(result, script, handledBlocksId, rootBlocks);
        }
        return result.toString();
    }

    @Override
    public String getTranslatedScript(int id) throws ServiceException {
        return buildScript(loadScript(id));
    }

    private void cacheContexts() throws ServiceException {
        try {
            List<Context> contexts = contextDao.getAll();
            for (Context context : contexts) {
                for (Extension extension : context.getExtensions()) {
                    if (extension.getScript() != null) {
                        extension.setScript(loadScript(extension.getScriptId()));
                    }
                }
            }
            cachedContexts = translateContexts(contexts);
        } catch (CannotCreateTransactionException | DaoException e) {
            throw new ServiceException("Could not load the context list", e);
        }

    }

    private String translateContexts(List<Context> contexts) {
        StringBuilder result = new StringBuilder();
        for (Context context : contexts) {
            result.append("[" + context.getName() + "]\n");
            for (Extension extension : context.getExtensions()) {
                result.append("exten = " + extension.getName() + ",1,NoOp()\n");
                if (extension.getScript() != null) {
                    result.append(buildScript(extension.getScript()));
                }
            }
        }
        return result.toString();
    }

    private void cacheGlobalVariables() throws ServiceException {
        StringBuilder stringBuilder = new StringBuilder("[globals](+)\n");
        stringBuilder.append("ASTERGAZER_HOST=");
        stringBuilder.append(configurationService.getFastAgiHost().getValue());
        stringBuilder.append("\n\n");
        cachedGlobalVariables = stringBuilder.toString();
    }

    @Override
    public String getTranslatedDialplan() {
        Long startTimeMs = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        try {
            cacheGlobalVariables();
            cacheContexts();
        } catch (ServiceException e) {
            log.error("Could not load dialplan", e);
            result.append(buildDialplanLoadErrorComment());
        }
        result.append(cachedGlobalVariables);
        result.append(cachedContexts);
        Long endTimeMs = System.currentTimeMillis();
        result.append(buildSummaryInfo(endTimeMs - startTimeMs));
        return result.toString();

    }

    private String buildSummaryInfo(long handlingTime) {
        StringBuilder result = new StringBuilder("\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
        result.append("; **********\n");
        result.append("; Generated by Astergazer\n");
        result.append("; Elapsed time: " + handlingTime + "ms\n");
        result.append("; ");
        result.append(dateFormat.format(new Date()));
        result.append("\n; **********\n");
        return result.toString();
    }

    private String buildDialplanLoadErrorComment() {
        StringBuilder result = new StringBuilder();
        result.append("; **********\n");
        result.append("; WARNING! Could not load dialplan, used cached one.\n");
        result.append("; **********\n");
        return result.toString();
    }
}
