package net.proselyte.pmsystem.model;

/**
 * Simple domain object that represents a Project
 *
 * @author Oleksii Samantsov
 */
public class Project extends NamedEntity {

    private String description;


    public Project() {
    }

    public Project(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Project{" + super.toString() + '\'' +
                "description='" + description + '\'' +
                "} ";
    }
}
