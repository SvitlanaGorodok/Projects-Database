package entities.dao;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class CustomerDao {
    private Integer id;
    private String name;
    private String description;
    private Set<ProjectDao> projects;
    public CustomerDao() {}
    public CustomerDao(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 30, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 100, nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(mappedBy = "customers")
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }
}
