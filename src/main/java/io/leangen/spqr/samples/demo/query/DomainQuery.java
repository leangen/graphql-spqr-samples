package io.leangen.spqr.samples.demo.query;

import io.leangen.spqr.samples.demo.dto.Customer;
import io.leangen.spqr.samples.demo.dto.Person;

/**
 * Created by loshmee on 2-10-16.
 */
public class DomainQuery {
    /**
     * Hello world greeting.
     *
     * Invoke with:
     * {greeting(Person: {firstName: "John", lastName: "Doe"})}
     *
     * @param person Person to greet
     * @return Informal hello string
     */
    public String getGreeting(final Person person){
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
    public String getGreeting(final Customer customer){
        return "Hello "+ customer.getPersonalTitle()+" "+customer.getLastName()+"!";
    }
}
