package io.leangen.spqr.samples.demo.dto;

import io.leangen.graphql.annotations.GraphQLQuery;

public class Person {
    private String firstName;
    private String lastName;
    
    @GraphQLQuery(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @GraphQLQuery(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        return lastName != null ? lastName.equals(person.lastName) : person.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
