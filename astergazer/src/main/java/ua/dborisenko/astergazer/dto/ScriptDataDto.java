package ua.dborisenko.astergazer.dto;

import java.util.List;

import ua.dborisenko.astergazer.domain.Connection;
import ua.dborisenko.astergazer.domain.Script;
import ua.dborisenko.astergazer.domain.block.Block;

public class ScriptDataDto {

    private List<Block> blocks;

    private List<Connection> connections;

    private String modificationStamp;

    public ScriptDataDto() {
    }

    public ScriptDataDto(Script script) {
        this.blocks = script.getBlocks();
        this.connections = script.getConnections();
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public String getModificationStamp() {
        return modificationStamp;
    }

    public void setModificationStamp(String modificationStamp) {
        this.modificationStamp = modificationStamp;
    }
}
