package io.leangen.graphql.samples.config;

import io.leangen.graphql.samples.repo.TaskRepo;
import io.leangen.graphql.samples.repo.ProjectRepo;
import io.leangen.graphql.samples.service.ProjectService;
import io.leangen.graphql.samples.service.TaskService;
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
    public TaskService taskService() {
        return new TaskService(taskRepo());
    }

    @Bean
    public TaskRepo taskRepo() {
        return new TaskRepo(projectRepo());
    }
}
