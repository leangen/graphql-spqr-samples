package io.leangen.spqr.samples.demo.query;

import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * Created by loshmee on 25-9-16.
 */
public class User {
    private String userName;
    private String fullName;
    
    @GraphQLQuery(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @GraphQLQuery(name = "fullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        return fullName != null ? fullName.equals(user.fullName) : user.fullName == null;

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }
}
