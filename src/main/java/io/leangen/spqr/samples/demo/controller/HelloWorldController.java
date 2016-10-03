package io.leangen.spqr.samples.demo.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaBuilder;
import io.leangen.graphql.metadata.strategy.query.BeanResolverExtractor;
import io.leangen.spqr.samples.demo.query.DomainQuery;
import io.leangen.spqr.samples.demo.query.PersonQuery;
import io.leangen.spqr.samples.demo.query.VendorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloWorldController {
    
    private GraphQL graphQlFromAnnotated;
    private GraphQL graphQLFromDomain;

    @Autowired
    public HelloWorldController(VendorQuery vendorQuery) {
        GraphQLSchema schemaFromAnnotated = new GraphQLSchemaBuilder()
                .withSingletonQuerySource(new PersonQuery())
                .withDefaults()
                .build();
        graphQlFromAnnotated = new GraphQL(schemaFromAnnotated);

        GraphQLSchema schemaFromDomain = new GraphQLSchemaBuilder()
                .withResolverExtractors(new BeanResolverExtractor())
                .withSingletonQuerySource(new DomainQuery())
//                .withSingletonQuerySource(vendorQuery)
                .withDefaults()
                .build();
        graphQLFromDomain = new GraphQL(schemaFromDomain);
    }

    @RequestMapping("/")
	public String welcome() {//Welcome page, non-rest
		return "Welcome to GraphQL-SPQR Demo app.";
	}
    
    @RequestMapping(value = "/graphql-from-annotation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(@RequestBody Map<String, Object> request) {
        final ExecutionResult executionResult = graphQlFromAnnotated.execute(request.get("query").toString());

        if (!executionResult.getErrors().isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (GraphQLError graphQLError : executionResult.getErrors()) {
                stringBuilder.append(graphQLError.getMessage()).append(";;;;;;");
            }
            return stringBuilder.toString();
        }

        return executionResult.getData();
    }

    @RequestMapping(value = "/graphql-from-domain", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromDomain(@RequestBody Map<String, Object> request) {
        return graphQLFromDomain.execute(request.get("query").toString()).getData();
    }
}
