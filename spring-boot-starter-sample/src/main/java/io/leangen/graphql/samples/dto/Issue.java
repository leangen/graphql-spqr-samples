package io.leangen.graphql.samples.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Issue {

    private String code;
    private String description;
    private Status status;

    @JsonCreator
    public Issue(String code, String description, Status status) {
        this.code = code;
        this.description = description;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}