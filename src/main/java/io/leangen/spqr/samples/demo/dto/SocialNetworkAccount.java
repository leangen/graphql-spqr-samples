package io.leangen.spqr.samples.demo.dto;

import io.leangen.graphql.annotations.GraphQLQuery;

public class SocialNetworkAccount {
    private String networkName;
    private String username;
    private Long numberOfConnections;
    
    @GraphQLQuery(name = "networkName")
    public String getNetworkName() {
        return networkName;
    }
    
    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }
    
    @GraphQLQuery(name = "username")
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @GraphQLQuery(name = "numberOfConnections")
    public Long getNumberOfConnections() {
        return numberOfConnections;
    }
    
    public void setNumberOfConnections(Long numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }
}
