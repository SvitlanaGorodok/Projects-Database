package entities.dao;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "skills")
public class SkillDao {
    private Integer id;
    private String area;
    private String level;
    private Set<DeveloperDao> developers;
    public SkillDao() {}
    public SkillDao(Integer id, String area, String level) {
        this.id = id;
        this.area = area;
        this.level = level;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "area", length = 30, nullable = false)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "level", length = 30, nullable = false)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @ManyToMany(mappedBy = "skills")
    public Set<DeveloperDao> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDao> developers) {
        this.developers = developers;
    }
}
