package io.leangen.graphql.samples.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Task {

    private String code;
    private String description;
    private Type type;
    private Status status;

    @JsonCreator
    public Task(String code, String description, Status status, Type type) {
        this.code = code;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
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