package io.leangen.graphql.samples.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.execution.relay.Page;
import io.leangen.graphql.samples.dto.Filter;
import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.samples.dto.Task;
import io.leangen.graphql.samples.repo.ProjectRepo;
import io.leangen.graphql.samples.repo.TaskRepo;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.dataloader.DataLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    //Relay page! Not Spring Data page!
    public Page<Project> projects(
            @GraphQLArgument(name = "limit", defaultValue = "10") int limit,
            @GraphQLArgument(name = "offset", defaultValue = "0") int offset,
            @GraphQLArgument(name = "filters", description = "Filters to apply")
            List<@GraphQLNonNull Filter> filters,
            String... tags
    ) {
        return repo.findProjects(limit, offset, tags);
    }

    @GraphQLQuery
    //Spring Data page. Toggle graphql.spqr.relay.spring-data-compatible for Relay compliant mapping
    public org.springframework.data.domain.Page<Project> projectsData(Pageable paging, String... tags) {
        return repo.findProjects(paging, tags);
    }

    @GraphQLQuery
    public Project project(@GraphQLContext Task task) {
        return project(TaskRepo.getProjectCode(task.getCode()));
    }

    @GraphQLQuery
    public CompletableFuture<Long> currentFunding(@GraphQLContext Project project, @GraphQLEnvironment ResolutionEnvironment env) {
        DataLoader<Project, Long> fundingLoader = env.dataFetchingEnvironment.getDataLoader("funding");
        return fundingLoader.load(project);
    }
}
