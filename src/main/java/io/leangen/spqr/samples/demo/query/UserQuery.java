package io.leangen.spqr.samples.demo.query;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Created by loshmee on 25-9-16.
 */
public class UserQuery {
    @GraphQLQuery
    public String getUsername(User user){
        return user.getUserName();
    }
}
