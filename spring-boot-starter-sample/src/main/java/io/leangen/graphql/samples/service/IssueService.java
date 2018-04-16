package io.leangen.graphql.samples.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.samples.dto.Issue;
import io.leangen.graphql.samples.dto.Status;
import io.leangen.graphql.samples.repo.IssueRepo;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@GraphQLApi
@Service
public class IssueService {

    private final IssueRepo repo;
    private final Map<String, FluxSink<Issue>> sinks = new ConcurrentHashMap<>();

    public IssueService(IssueRepo repo) {
        this.repo = repo;
    }

    @GraphQLMutation
    public Issue createIssue(String projectCode, String description, @GraphQLArgument(name = "status", defaultValue = "\"BACKLOG\"") Status status) {
        return repo.saveIssue(projectCode, description, status);
    }

    @GraphQLMutation
    public Issue updateIssue(@GraphQLNonNull String code, String description, @GraphQLNonNull Status status) {
        Issue issue = repo.byCode(code);
        if (description != null) {
            issue.setDescription(description);
        }
        issue.setStatus(status);
        if (sinks.containsKey(code)) {
            sinks.get(code).next(issue);
        }
        return issue;
    }

    @GraphQLQuery
    public Collection<Issue> issues(String projectCode, Status... statuses) {
        return repo.byProjectCodeAndStatus(projectCode, statuses);
    }

    @GraphQLQuery
    public Issue issue(String code) {
        return repo.byCode(code);
    }

    @GraphQLSubscription
    public Publisher<Issue> issueStatusChanged(String code) {
        return Flux.create(fluxSink -> sinks.put(code, fluxSink));
    }
}
