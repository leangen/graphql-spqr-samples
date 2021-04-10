package io.leangen.graphql.samples.repo;

import io.leangen.graphql.execution.relay.Page;
import io.leangen.graphql.execution.relay.generic.PageFactory;
import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.samples.dto.Task;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProjectRepo {

    private final Map<String, Project> projects = new HashMap<>();

    public Project save(String name, List<String> tags) {
        String code = generateCode(name);
        Project project = new Project(code, name, new ArrayList<>(), tags);
        projects.put(code, project);
        return project;
    }

    public void addTask(String code, Task task) {
        projects.get(code).addTask(task);
    }

    public Project byCode(String code) {
        return projects.get(code);
    }

    //Relay page! Not Spring Data page!
    public Page<Project> findProjects(int limit, int offset, String... tags) {
        List<Project> filteredByTag = findByTags(limit,offset, tags);
        List<Project> page = filteredByTag.subList(offset, Math.min(offset + limit, filteredByTag.size()));
        return PageFactory.createOffsetBasedPage(page, filteredByTag.size(), offset);
    }

    //SpringData page
    public org.springframework.data.domain.Page<Project> findProjects(Pageable paging, String... tags) {
        int offset = paging.getPageNumber() * paging.getPageSize();
        int limit = offset + paging.getPageSize();
        List<Project> filteredByTag = findByTags(limit, offset, tags);
        List<Project> page = filteredByTag.subList(offset, Math.min(offset + limit, filteredByTag.size()));
        return new PageImpl<>(page, paging, filteredByTag.size());
    }

    private List<Project> findByTags(int limit, int offset, String... tags) {
        return projects.values().stream()
                .filter(project -> tags == null || tags.length == 0 || Arrays.stream(tags).anyMatch(tag ->
                        project.getTags().contains(tag)))
                .collect(Collectors.toList());
    }

    private String generateCode(String name) {
        if (name.length() < 3) {
            return name.toUpperCase() + "XX";
        }
        List<String> chars = new ArrayList<>();
        boolean take = false;
        for(char c : name.toCharArray()) {
            if (Character.isUpperCase(c) || take) {
                chars.add(Character.toUpperCase(c) + "");
                take = false;
            }
            if (c == ' ') {
                take = true;
            }
        }
        if (chars.size() < 3) {
            chars.add(1, name.substring(1, 3).toUpperCase());
        }
        return String.join("", chars);
    }
}
