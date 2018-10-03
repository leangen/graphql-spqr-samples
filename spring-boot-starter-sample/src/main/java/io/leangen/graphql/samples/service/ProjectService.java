package io.leangen.graphql.samples.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.samples.dto.Task;
import io.leangen.graphql.samples.repo.TaskRepo;
import io.leangen.graphql.samples.repo.ProjectRepo;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    public Project project(@GraphQLContext Task task) {
        return project(TaskRepo.getProjectCode(task.getCode()));
    }

    @GraphQLQuery
    public long currentFunding(@GraphQLContext Project project) {
        return ThreadLocalRandom.current().nextInt(1000) * 1000;
    }
}
