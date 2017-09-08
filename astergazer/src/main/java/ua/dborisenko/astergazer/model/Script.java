package ua.dborisenko.astergazer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import ua.dborisenko.astergazer.model.block.Block;

@Entity
@Table(name = "dlp_script")
@NamedQueries(value = {
        @NamedQuery(name = "Script.getAll", query = "SELECT s FROM Script s order by s.name"),
        @NamedQuery(name = "Script.getCount", query = "SELECT count(s) FROM Script s WHERE s.name = :name and s.id <> :id") })
public class Script implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("localId")
    private List<Block> blocks = new ArrayList<>();

    @OneToMany(mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> connections = new ArrayList<>();

    @Column(name = "modification_stamp")
    private String modificationStamp;

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public String getModificationStamp() {
        return modificationStamp;
    }

    public void setModificationStamp(String modificationStamp) {
        this.modificationStamp = modificationStamp;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Script resultScript = (Script) super.clone();
        resultScript.setId(null);
        resultScript.setModificationStamp(null);
        resultScript.blocks = new ArrayList<>();
        for (Block block : blocks) {
            Block resultBlock = (Block) block.clone();
            resultBlock.setScript(resultScript);
            resultScript.getBlocks().add(resultBlock);
        }
        resultScript.connections = new ArrayList<>();
        for (Connection connection : connections) {
            Connection resultConnection = (Connection) connection.clone();
            resultConnection.setScript(resultScript);
            resultScript.getConnections().add(resultConnection);
        }
        return resultScript;
    }
}
