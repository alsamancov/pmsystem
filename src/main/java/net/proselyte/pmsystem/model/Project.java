package net.proselyte.pmsystem.model;

/**
 * Simple domain object that represents a Project
 *
 * @author Oleksii Samantsov
 */
public class Project extends NamedEntity {

    private String description;

    private Long companyId;
    private Long documentId;
    private Long teamId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

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
