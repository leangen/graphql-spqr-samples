package io.leangen.spqr.samples.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import io.leangen.spqr.samples.demo.query.annotated.PersonQuery;
import io.leangen.spqr.samples.demo.query.annotated.SocialNetworkQuery;
import io.leangen.spqr.samples.demo.query.annotated.VendorQuery;
import io.leangen.spqr.samples.demo.query.unannotated.DomainQuery;
import io.leangen.spqr.samples.demo.query.unannotated.ProductQuery;

@RestController
public class GraphQlSampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQlSampleController.class);

    private final GraphQL graphQlFromAnnotated;

    @Autowired
    public GraphQlSampleController(PersonQuery personQuery,
                                   SocialNetworkQuery socialNetworkQuery,
                                   DomainQuery domainQuery,
                                   ProductQuery productQuery,
                                   VendorQuery vendorQuery) {

        //Schema generated from query classes
        GraphQLSchema schemaFromAnnotated = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        //Resolve by annotations
                        new AnnotatedResolverBuilder(),
                        //Resolve public methods inside root package
                        new PublicResolverBuilder("io.leangen.spqr.samples.demo"))
                .withOperationsFromSingleton(personQuery)
                .withOperationsFromSingleton(socialNetworkQuery)
                .withOperationsFromSingleton(vendorQuery)
                .withOperationsFromSingleton(domainQuery)
                .withOperationsFromSingleton(productQuery)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        graphQlFromAnnotated = GraphQL.newGraphQL(schemaFromAnnotated).build();
        LOGGER.info("Generated GraphQL schema using SPQR");
    }

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(@RequestBody Map<String, Object> request) {
        ExecutionResult executionResult = graphQlFromAnnotated.execute((String) request.get("query"), (String) request.get("operationName"), (Object) null);

        if (!executionResult.getErrors().isEmpty()) {
            return sanitize(executionResult);
        }

        return executionResult;
    }

    //Just mocking error handling
    private ExecutionResult sanitize(ExecutionResult executionResult) {
        return new ExecutionResultImpl(executionResult.getErrors().stream()
                .peek(err -> LOGGER.error(err.getMessage()))
                .map(this::sanitize)
                .collect(Collectors.toList()));
    }
    
    private GraphQLError sanitize(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            return new GraphQLError() {
                @Override
                public String getMessage() {
                    Throwable cause = ((ExceptionWhileDataFetching) error).getException().getCause();
                    return cause instanceof InvocationTargetException ? ((InvocationTargetException) cause).getTargetException().getMessage() : cause.getMessage();
                }

                @Override
                public List<SourceLocation> getLocations() {
                    return error.getLocations();
                }

                @Override
                public ErrorType getErrorType() {
                    return error.getErrorType();
                }
            };
        }
        return error;
    }
}
