package net.proselyte.pmsystem.model;

import java.util.Set;

/**
 * Simple domain object tha represents a Customer
 *
 * @author Oleksii Samantsov
 */
public class Customer extends NamedEntity {

    private String description;

    private Set<Project> projects;

    public Customer() {
    }

    public Customer(String description, Set<Project> projects) {
        this.description = description;
        this.projects = projects;
    }

    public Customer(String name, String description, Set<Project> projects) {
        super(name);
        this.description = description;
        this.projects = projects;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }


    @Override
    public String toString() {
        return "Customer{" + super.toString() + '\'' +
                ", description='" + description +
                "} " ;
    }
}
