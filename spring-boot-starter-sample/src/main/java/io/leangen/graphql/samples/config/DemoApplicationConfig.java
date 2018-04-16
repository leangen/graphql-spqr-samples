package io.leangen.graphql.samples.config;

import io.leangen.graphql.samples.repo.IssueRepo;
import io.leangen.graphql.samples.repo.ProjectRepo;
import io.leangen.graphql.samples.service.IssueService;
import io.leangen.graphql.samples.service.ProjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoApplicationConfig {

    @Bean
    public ProjectService projectService() {
        return new ProjectService(projectRepo());
    }

    @Bean
    public ProjectRepo projectRepo() {
        return new ProjectRepo();
    }

    @Bean
    public IssueService issueService() {
        return new IssueService(issueRepo());
    }

    @Bean
    public IssueRepo issueRepo() {
        return new IssueRepo(projectRepo());
    }
}
