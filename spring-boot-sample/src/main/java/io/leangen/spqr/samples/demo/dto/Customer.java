package io.leangen.spqr.samples.demo.dto;

import io.leangen.graphql.annotations.GraphQLQuery;

public class Customer extends Person {
    private PersonalTitle personalTitle;

    @GraphQLQuery(name = "title")
    public PersonalTitle getPersonalTitle() {
        return personalTitle;
    }

    public void setPersonalTitle(PersonalTitle personalTitle) {
        this.personalTitle = personalTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return personalTitle == customer.personalTitle;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (personalTitle != null ? personalTitle.hashCode() : 0);
        return result;
    }
}
