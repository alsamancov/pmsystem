package net.proselyte.pmsystem.model;

import java.util.Set;

/**
 * Simple domain object that represents a Company
 *
 * @author Oleksii Samantsov
 */
public class Company extends NamedEntity {

    private String description;

    private Set<Customer> customers;

    public Company() {
    }

    public Company(String description, Set<Customer> customers) {
        this.description = description;
        this.customers = customers;
    }

    public Company(String name, String description, Set<Customer> customers) {
        super(name);
        this.description = description;
        this.customers = customers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Company{" + super.toString() + '\'' +
                ", description='" + description + '\'' +
                ", customers=" + customers +
                "} ";
    }
}
