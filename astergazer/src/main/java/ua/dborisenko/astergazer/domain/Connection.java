package ua.dborisenko.astergazer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dlp_connection")
public class Connection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "script_id")
    @JsonIgnore
    private Script script;
    
    @Column(name = "source_block_local_id")
    private int sourceBlockLocalId;
    
    @Column(name = "target_block_local_id")
    private int targetBlockLocalId;
    
    @Column(name = "is_locked")
    private boolean isLocked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public int getSourceBlockLocalId() {
        return sourceBlockLocalId;
    }

    public void setSourceBlockLocalId(int sourceBlockLocalId) {
        this.sourceBlockLocalId = sourceBlockLocalId;
    }

    public int getTargetBlockLocalId() {
        return targetBlockLocalId;
    }

    public void setTargetBlockLocalId(int targetBlocklocalId) {
        this.targetBlockLocalId = targetBlocklocalId;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
