package ua.dborisenko.astergazer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "dlp_checklist_entry")
@NamedQueries(value = {
        @NamedQuery(name = "ChecklistEntry.getCount", query = "SELECT count(e) FROM ChecklistEntry e WHERE e.controlValue = :controlValue and e.checklist.id = :checklistId and e.id <> :id"),
        @NamedQuery(name = "ChecklistEntry.getReturnValue", query = "SELECT e.returnValue FROM ChecklistEntry e WHERE e.checklist.id = :checklistId and e.controlValue = :controlValue") })
public class ChecklistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    @Column(name = "control_value")
    private String controlValue;

    @Column(name = "return_value")
    private String returnValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

}
