package io.leangen.spqr.samples.demo.controller;

import graphql.ExecutionInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import graphql.ExecutionResult;
import graphql.GraphQL;
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

import javax.servlet.http.HttpServletRequest;

@RestController
public class GraphQLSampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLSampleController.class);

    private final GraphQL graphQL;

    @Autowired
    public GraphQLSampleController(PersonQuery personQuery,
                                   SocialNetworkQuery socialNetworkQuery,
                                   DomainQuery domainQuery,
                                   ProductQuery productQuery,
                                   VendorQuery vendorQuery) {

        //Schema generated from query classes
        GraphQLSchema schema = new GraphQLSchemaGenerator()
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
        graphQL = GraphQL.newGraphQL(schema).build();

        LOGGER.info("Generated GraphQL schema using SPQR");
    }

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> indexFromAnnotated(@RequestBody Map<String, String> request, HttpServletRequest raw) {
        ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
                .query(request.get("query"))
                .operationName(request.get("operationName"))
                .context(raw)
                .build());
        return executionResult.toSpecification();
    }
}