package entities.dao;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ProjectDao {
    private Integer id;
    private String name;
    private String description;
    private Date startDate;

    private Set<DeveloperDao> developers;
    private Set<CompanyDao> companies;
    private Set<CustomerDao> customers;
    public ProjectDao() { }

    public ProjectDao(Integer id, String name, String description, Date startDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
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

    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @ManyToMany
    @JoinTable (
            name = "developers_projects",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "developer_id") }
    )
    public Set<DeveloperDao> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDao> developers) {
        this.developers = developers;
    }

    @ManyToMany
    @JoinTable (
            name = "companies_projects",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "company_id") }
    )
    public Set<CompanyDao> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<CompanyDao> companies) {
        this.companies = companies;
    }

    @ManyToMany
    @JoinTable (
            name = "customers_projects",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "customer_id") }
    )
    public Set<CustomerDao> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<CustomerDao> customers) {
        this.customers = customers;
    }
}
