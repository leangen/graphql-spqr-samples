package io.leangen.spqr.samples.demo.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaBuilder;
import io.leangen.graphql.metadata.strategy.query.BeanResolverExtractor;
import io.leangen.spqr.samples.demo.query.DomainQuery;
import io.leangen.spqr.samples.demo.query.PersonQuery;

@RestController
public class HelloWorldController {
    
    private GraphQL graphQlFromAnnotated;
    private GraphQL graphQLFromDomain;

    public HelloWorldController() {
        GraphQLSchema schemaFromAnnotated = new GraphQLSchemaBuilder()
                .withSingletonQuerySource(new PersonQuery())
                .withDefaults()
                .build();
        graphQlFromAnnotated = new GraphQL(schemaFromAnnotated);

        GraphQLSchema schemaFromDomain = new GraphQLSchemaBuilder()
		        .withResolverExtractors(new BeanResolverExtractor())
		        .withSingletonQuerySource(new DomainQuery())
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
        return graphQlFromAnnotated.execute(request.get("query").toString()).getData();
    }

    @RequestMapping(value = "/graphql-from-domain", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromDomain(@RequestBody Map<String, Object> request) {
        return graphQLFromDomain.execute(request.get("query").toString()).getData();
    }
}
