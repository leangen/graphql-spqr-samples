package io.leangen.graphql.samples.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private final String code;
    private final String name;
    private final List<Issue> issues;
    private final List<String> tags;

    public Project(String name) {
        this(null, name, new ArrayList<>(), new ArrayList<>());
    }

    @JsonCreator
    public Project(String code, String name, List<Issue> issues, List<String> tags) {
        this.code = code;
        this.name = name;
        this.issues = issues;
        this.tags = tags;
    }

    public void addIssue(Issue issue) {
        this.issues.add(issue);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public List<String> getTags() {
        return tags;
    }
}
