package io.leangen.spqr.samples.demo.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.BeanResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import io.leangen.spqr.samples.demo.query.annotated.PersonQuery;
import io.leangen.spqr.samples.demo.query.annotated.SocialNetworkQuery;
import io.leangen.spqr.samples.demo.query.annotated.VendorQuery;
import io.leangen.spqr.samples.demo.query.unannotated.DomainQuery;
import io.leangen.spqr.samples.demo.query.unannotated.ProductQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

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

        //Schema generated from annotated query classes
        GraphQLSchema schemaFromAnnotated = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        //Discover by annotations
                        new AnnotatedResolverBuilder(),
                        //Discover public methods inside root package
                        new PublicResolverBuilder("io.leangen.spqr.samples.demo"))
                .withOperationsFromSingleton(personQuery)
                .withOperationsFromSingleton(socialNetworkQuery)
                .withOperationsFromSingleton(vendorQuery)
                .withOperationsFromSingleton(domainQuery)
                .withOperationsFromSingleton(productQuery)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        graphQlFromAnnotated = GraphQL.newGraphQL(schemaFromAnnotated).build();
        LOGGER.debug("Generated schema from query classes");
    }

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(@RequestBody Map<String, Object> request) {
        final ExecutionResult executionResult = graphQlFromAnnotated.execute(request.get("query").toString());

        if (!executionResult.getErrors().isEmpty()) {
            return extractErrorResponse(executionResult);
        }

        return executionResult;
    }

    //Just mocking error handling
    private Object extractErrorResponse(ExecutionResult executionResult) {
        return executionResult.getErrors()
                .stream()
                .map(GraphQLError::getMessage)
                .collect(Collectors.toList());
    }
}
