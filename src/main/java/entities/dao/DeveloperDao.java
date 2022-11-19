package entities.dao;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "developers")
public class DeveloperDao {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer salary;

    private Set<ProjectDao> projects;
    private Set<SkillDao> skills;

    public DeveloperDao() { }

    public DeveloperDao(String firstName, String lastName, Integer age, Integer salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }
    public DeveloperDao(Integer id, String firstName, String lastName, Integer age, Integer salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "first_name", length = 30, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", length = 30, nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    @Column(name = "salary")
    public Integer getSalary() {
        return salary;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @ManyToMany(mappedBy = "developers")
    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }

    @ManyToMany
    @JoinTable (
            name = "developers_skills",
            joinColumns = { @JoinColumn(name = "developer_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
    public Set<SkillDao> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDao> skills) {
        this.skills = skills;
    }
}
