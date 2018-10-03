package io.leangen.graphql.samples.repo;

import io.leangen.graphql.samples.dto.Task;
import io.leangen.graphql.samples.dto.Project;
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

    public List<Project> byTags(String... tags) {
        return projects.values().stream()
                .filter(project -> tags == null || Arrays.stream(tags).anyMatch(tag ->
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
