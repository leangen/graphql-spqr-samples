package io.leangen.spqr.samples.demo.dto;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Created by loshmee on 27-9-16.
 */
public class Customer extends Person {
    private PersonalTitle personalTitle;

    @GraphQLQuery(name = "title")
    public PersonalTitle getPersonalTitle() {
        return personalTitle;
    }

    public void setPersonalTitle(PersonalTitle personalTitle) {
        this.personalTitle = personalTitle;
    }
}
