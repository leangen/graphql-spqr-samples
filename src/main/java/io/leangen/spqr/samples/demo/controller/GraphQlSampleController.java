package io.leangen.spqr.samples.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.BeanResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import io.leangen.spqr.samples.demo.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GraphQlSampleController {

    private final GraphQL graphQlFromAnnotated;
    private final GraphQL graphQLFromDomain;

    @Autowired
    public GraphQlSampleController(PersonQuery personQuery,
                                   SocialNetworkQuery socialNetworkQuery,
                                   DomainQuery domainQuery,
                                   ProductQuery productQuery,
                                   VendorQuery vendorQuery) {

        //Schema generated from annotated query classes
        GraphQLSchema schemaFromAnnotated = new GraphQLSchemaGenerator()
                .withOperationsFromSingleton(personQuery)
                .withOperationsFromSingleton(socialNetworkQuery)
                .withOperationsFromSingleton(vendorQuery)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                //Shortcut method to set usage of default resolver extractors, type mappers and value converters
                .withDefaults()
                .generate();
        graphQlFromAnnotated = GraphQL.newGraphQL(schemaFromAnnotated).build();

        //Schema generated from unannotated classes
        GraphQLSchema schemaFromDomain = new GraphQLSchemaGenerator()
                //Using the provided query extractor that relies on the java bean convention
                .withResolverBuilders(new BeanResolverBuilder(null))
                .withOperationsFromSingleton(domainQuery)
                .withOperationsFromSingleton(productQuery)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .withDefaults()
                .generate();
        graphQLFromDomain = GraphQL.newGraphQL(schemaFromDomain).build();
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(@RequestBody Map<String, Object> request) {
        final ExecutionResult executionResult = graphQlFromAnnotated.execute(request.get("query").toString());

        if (!executionResult.getErrors().isEmpty()) {
            return extractErrorResponse(executionResult);
        }

        return executionResult;
    }

    @RequestMapping(value = "/graphql-from-domain", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromDomain(@RequestBody Map<String, Object> request) {
        ExecutionResult executionResult = graphQLFromDomain.execute(request.get("query").toString());

        if (!executionResult.getErrors().isEmpty()) {
            return extractErrorResponse(executionResult);
        }

        return executionResult.getData();
    }

    //Just mocking error handling
    private Object extractErrorResponse(ExecutionResult executionResult) {
        return executionResult.getErrors()
                .stream()
                .map(GraphQLError::getMessage)
                .collect(Collectors.toList());
    }
}
