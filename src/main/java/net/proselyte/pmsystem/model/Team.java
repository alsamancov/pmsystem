package net.proselyte.pmsystem.model;

/**
 * Simple domain object that represents Team of {@link Developer}s
 */
public class Team extends NamedEntity {

    public Team() {
    }

    public Team(String name) {
        super(name);
    }

    public Team(Long id, String name){
        super(name);
        this.setId(id);
    }

    @Override
    public String toString() {
        return "Team{} " + super.toString();
    }
}
