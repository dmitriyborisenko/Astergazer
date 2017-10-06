package ua.dborisenko.astergazer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dlp_context")
@NamedQueries(value = {
        @NamedQuery(name = "Context.getAll", query = "SELECT c FROM Context c order by c.name"),
        @NamedQuery(name = "Context.getCount", query = "SELECT count(c) FROM Context c WHERE c.name = :name and c.id <> :id") })
public class Context implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "context", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Extension> extensions = new ArrayList<>();

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

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Context resultContext = (Context) super.clone();
        resultContext.setId(null);
        List<Extension> resultExtensions = new ArrayList<>();
        for (Extension extension : extensions) {
            Extension resultExtension = (Extension) extension.clone();
            resultExtension.setContext(resultContext);
            resultExtensions.add(resultExtension);
        }
        resultContext.setExtensions(resultExtensions);
        return resultContext;
    }

}
