package io.leangen.spqr.samples.demo.query.annotated;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.spqr.samples.demo.dto.Person;
import io.leangen.spqr.samples.demo.dto.SocialNetworkAccount;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by milos on 5-10-16.
 */
@Component
public class SocialNetworkQuery {
    private Random random;

    public SocialNetworkQuery() {
        random = new Random();
    }

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
            @GraphQLContext Person person) throws InterruptedException {
        Set<SocialNetworkAccount> mockResult = new LinkedHashSet<>();
        
        SocialNetworkAccount twitterAccount = new SocialNetworkAccount();
        twitterAccount.setNetworkName("Twitter");
        twitterAccount.setUsername(generateMockUsername(person.getFirstName()));
        twitterAccount.setNumberOfConnections(250L);
        mockResult.add(twitterAccount);
        
        SocialNetworkAccount facebookAcount = new SocialNetworkAccount();
        facebookAcount.setNetworkName("Facebook");
        facebookAcount.setUsername(generateMockUsername(person.getLastName()));
        facebookAcount.setNumberOfConnections(2311L);
        mockResult.add(facebookAcount);

        //Mocking slow external API access
        Thread.sleep(1000L);

        return mockResult;
    }

    private String generateMockUsername(String base){
        final int randomSuffix =  100 + this.random.nextInt(899);
        return base+randomSuffix;
    }
}
