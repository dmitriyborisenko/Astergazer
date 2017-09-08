package ua.dborisenko.astergazer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dlp_checklist")
@NamedQueries(value = {
        @NamedQuery(name = "Checklist.getByName", query = "SELECT c FROM Checklist c WHERE c.name = :name"),
        @NamedQuery(name = "Checklist.getAll", query = "SELECT c FROM Checklist c order by c.name"),
        @NamedQuery(name = "Checklist.getCount", query = "SELECT count(c) FROM Checklist c WHERE c.name = :name and c.id <> :id") })
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistEntry> entries = new ArrayList<>();

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

    public List<ChecklistEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ChecklistEntry> entries) {
        this.entries = entries;
    }
}
