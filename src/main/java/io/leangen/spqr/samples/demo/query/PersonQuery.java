package io.leangen.spqr.samples.demo.query;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.spqr.samples.demo.dto.Customer;
import io.leangen.spqr.samples.demo.dto.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loshmee on 25-9-16.
 */
public class PersonQuery {
    /**
     * Hello world greeting.
     *
     * Invoke with:
     * {greeting(Person: {firstName: "John", lastName: "Doe"})}
     *
     * @param person Person to greet
     * @return Informal hello string
     */
    @GraphQLQuery(name = "greeting")
    public String getGreeting(@GraphQLArgument(name = "Person", description = "Person to greet.")
                              final Person person){
        return "Hello "+ person.getFirstName()+"!";
    }


    /**
     * Hello world polite greeting.
     *
     * Invoke with:
     * {greeting(Customer: {firstName: "John", lastName: "Doe", title: MR})}
     *
     * @param customer Customer to greet politely
     * @return Informal hello string
     */
    @GraphQLQuery(name = "greeting")
    public String getGreeting(@GraphQLArgument(name = "Customer", description = "Customer to greet.")
                              final Customer customer){
        return "Hello "+ customer.getPersonalTitle()+" "+customer.getLastName()+"!";
    }

    @GraphQLQuery(name = "firstNPersons")
    public List<Person> getFirstNPersons(int count){
        List<Person> result = new ArrayList<>();

        Person p1 = new Person();
        p1.setFirstName("John");
        p1.setLastName("Doe");
        result.add(p1);

        Person p2 = new Person();
        p2.setFirstName("Jane");
        p2.setLastName("Doe");
        result.add(p2);

        return result;
    }
}
