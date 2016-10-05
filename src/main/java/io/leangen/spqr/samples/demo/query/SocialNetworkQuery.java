package io.leangen.spqr.samples.demo.query;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLResolverSource;
import io.leangen.spqr.samples.demo.dto.Person;
import io.leangen.spqr.samples.demo.dto.SocialNetworkAccount;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by milos on 5-10-16.
 */
@Component
public class SocialNetworkQuery {
    
    /**
     * Attaching an external query to the domain object result
     * (e.g. adding a set of SocialNetworkAccounts to a Person)
     *
     * Invoke with:
     * {firstNPersons(count: 5) {firstName, lastName, socialNetworkAccounts{networkName, username, numberOfConnections}}}
     *
     * @param person
     * @return
     */
    @GraphQLQuery(name = "socialNetworkAccounts")
    public Set<SocialNetworkAccount> getSocialNetworkAccounts(
            @GraphQLResolverSource Person person){
        Set<SocialNetworkAccount> mockResult = new LinkedHashSet<>();
        
        SocialNetworkAccount twitterAccount = new SocialNetworkAccount();
        twitterAccount.setNetworkName("Twitter");
        twitterAccount.setUsername("johny999");
        twitterAccount.setNumberOfConnections(250L);
        mockResult.add(twitterAccount);
        
        SocialNetworkAccount facebookAcount = new SocialNetworkAccount();
        facebookAcount.setNetworkName("Facebook");
        facebookAcount.setUsername("john.doe999");
        facebookAcount.setNumberOfConnections(2311L);
        mockResult.add(facebookAcount);
        
        return mockResult;
    }
}
