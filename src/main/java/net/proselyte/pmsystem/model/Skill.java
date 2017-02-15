package net.proselyte.pmsystem.model;

/**
 * Simple domain object that represents a Skill
 */
public class Skill extends NamedEntity{
    public Skill(){

    }

    public Skill(String name) {
        super(name);
    }

    public Skill(Long id, String name){
        super(name);
        this.setId(id);
    }



}
