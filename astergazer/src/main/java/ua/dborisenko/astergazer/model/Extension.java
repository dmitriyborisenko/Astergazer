package ua.dborisenko.astergazer.model;

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
@Table(name = "dlp_extension")
@NamedQueries(value = {
        @NamedQuery(name = "Extension.getCount", query = "SELECT count(e) FROM Extension e WHERE e.name = :name and e.context.id = :contextId and e.id <> :id"),
        @NamedQuery(name = "Extension.unlinkAllFromScript", query = "UPDATE Extension e SET e.script = null WHERE e.script.id = :id") })
public class Extension implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "context_id")
    private Context context;

    @ManyToOne
    @JoinColumn(name = "script_id")
    private Script script;

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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Long getScriptId() {
        return script.getId();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Extension result = (Extension) super.clone();
        result.setId(null);
        result.setContext(null);
        return result;
    }

}
