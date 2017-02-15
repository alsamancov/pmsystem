package net.proselyte.pmsystem.model;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Simple domain object that represents a Developer
 *
 * @author Oleksii Samantsov
 */
public class Developer extends NamedEntity {
    private String firstName;
    private String lastName;
    private Integer age;
    private BigDecimal salary;
    private Integer yearsOfExperience;
    private String experience;
    private Set<Skill> skills;

    public Developer() {
    }

    public Developer(String firstName, String lastName, Integer age, BigDecimal salary, Integer yearsOfExperience, String experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.experience = experience;
    }

    public Developer(String name, String firstName, String lastName, Integer age, BigDecimal salary, Integer yearsOfExperience, String experience) {
        super(name);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.experience = experience;
    }

    public Developer(String firstName, String lastName, Integer age, BigDecimal salary, Integer yearsOfExperience, String experience, Set<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.experience = experience;
        this.skills = skills;
    }

    public Developer(Long id, String name, String firstName, String lastName, Integer age, BigDecimal salary, Integer yearsOfExperience, String experience, Set<Skill> skills) {
        super(name);
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.yearsOfExperience = yearsOfExperience;
        this.experience = experience;
        this.skills = skills;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Developer{" + super.toString() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", yearsOfExperience=" + yearsOfExperience +
                ", experience='" + experience + '\'' +
                ", skills=" + skills +
                "} " ;
    }
}
