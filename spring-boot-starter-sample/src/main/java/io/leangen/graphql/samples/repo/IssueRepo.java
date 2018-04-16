package io.leangen.graphql.samples.repo;

import io.leangen.graphql.samples.dto.Issue;
import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.samples.dto.Status;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class IssueRepo {

    private final ProjectRepo projectRepo;

    public IssueRepo(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Issue saveIssue(String projectCode, String description, Status status) {
        Project project = projectRepo.byCode(projectCode);
        String code = projectCode + "-" + (project.getIssues().size() + 1);
        Issue saved = new Issue(code, description, status);
        projectRepo.addIssue(projectCode, saved);
        return saved;
    }

    public Issue byCode(String code) {
        String projectCode = getProjectCode(code);
        return projectRepo.byCode(projectCode).getIssues().stream()
                .filter(issue -> issue.getCode().equals(code))
                .findFirst().orElse(null);
    }

    public Collection<Issue> byProjectCodeAndStatus(String code, Status... statuses) {
        return projectRepo.byCode(code).getIssues().stream()
                .filter(issue -> statuses == null || Arrays.stream(statuses).anyMatch(status -> issue.getStatus() == status))
                .collect(Collectors.toList());
    }

    public static String getProjectCode(String issueCode) {
        return issueCode.substring(0, issueCode.indexOf("-"));
    }
}
