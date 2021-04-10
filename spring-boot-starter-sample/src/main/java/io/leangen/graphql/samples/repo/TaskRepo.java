package io.leangen.graphql.samples.repo;

import io.leangen.graphql.samples.dto.Task;
import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.samples.dto.Status;
import io.leangen.graphql.samples.dto.Type;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class TaskRepo {

    private final ProjectRepo projectRepo;

    public TaskRepo(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Task saveTask(String projectCode, String description, Status status, Type type) {
        Project project = projectRepo.byCode(projectCode);
        String code = projectCode + "-" + (project.getTasks().size() + 1);
        Task saved = new Task(code, description, status, type);
        projectRepo.addTask(projectCode, saved);
        return saved;
    }

    public Task byCode(String code) {
        String projectCode = getProjectCode(code);
        return projectRepo.byCode(projectCode).getTasks().stream()
                .filter(task -> task.getCode().equals(code))
                .findFirst().orElse(null);
    }

    public Collection<Task> byProjectCodeAndStatus(String projectCode, Status... statuses) {
        return projectRepo.byCode(projectCode).getTasks().stream()
                .filter(task -> statuses == null || Arrays.stream(statuses).anyMatch(status -> task.getStatus() == status))
                .collect(Collectors.toList());
    }

    public static String getProjectCode(String taskCode) {
        return taskCode.substring(0, taskCode.indexOf("-"));
    }
}
