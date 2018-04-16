package io.leangen.graphql.samples.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.samples.dto.Issue;
import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.samples.dto.Status;
import io.leangen.graphql.samples.repo.IssueRepo;
import io.leangen.graphql.samples.repo.ProjectRepo;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.List;

@GraphQLApi
@Service
public class ProjectService {

    private final ProjectRepo repo;

    public ProjectService(ProjectRepo repo) {
        this.repo = repo;
    }

    @GraphQLQuery
    public Project project(String code) {
        return repo.byCode(code);
    }

    @GraphQLMutation
    public Project createProject(String name, @GraphQLArgument(name = "tags", defaultValue = "[]") List<String> tags) {
        return repo.save(name, tags);
    }

    @GraphQLQuery
    public List<Project> projects(String... tags) {
        return repo.byTags(tags);
    }

    @GraphQLQuery
    public Project project(@GraphQLContext Issue issue) {
        return project(IssueRepo.getProjectCode(issue.getCode()));
    }

    @GraphQLQuery
    public boolean done(@GraphQLContext Project project) {
        return project.getIssues().stream().allMatch(issue -> issue.getStatus() == Status.DONE);
    }
}
