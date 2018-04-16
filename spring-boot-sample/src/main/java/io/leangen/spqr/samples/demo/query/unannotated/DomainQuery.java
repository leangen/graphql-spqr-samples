package io.leangen.spqr.samples.demo.query.unannotated;

import io.leangen.spqr.samples.demo.dto.Customer;
import io.leangen.spqr.samples.demo.dto.Person;
import org.springframework.stereotype.Component;

@Component
public class DomainQuery {
    /**
     * Hello world greeting.
     *
     * Invoke with:
     * {getNormalGreeting(person: {firstName: "John", lastName: "Doe"})}
     *
     * @param person Person to greet
     * @return Informal hello string
     */
    public String getNormalGreeting(final Person person){
        return "Hello "+ person.getFirstName()+"!";
    }


    /**
     * Hello world polite greeting.
     *
     * Invoke with:
     * {getPoliteGreeting(customer: {firstName: "John", lastName: "Doe", title: MR})}
     *
     * @param customer Customer to greet politely
     * @return Formal hello string
     */
    public String getPoliteGreeting(final Customer customer){
        return "Hello "+ customer.getPersonalTitle()+" "+customer.getLastName()+"!";
    }
}
