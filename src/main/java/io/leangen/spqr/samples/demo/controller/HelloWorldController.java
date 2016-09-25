package io.leangen.spqr.samples.demo.controller;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaBuilder;
import io.leangen.spqr.samples.demo.query.UserQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloWorldController {
    
    private GraphQL graphQl;

    public HelloWorldController() {
        GraphQLSchema schema = new GraphQLSchemaBuilder()
                .withSingletonQuerySource(new UserQuery())
                .build();
        graphQl = new GraphQL(schema);
    }

    @RequestMapping("/")
	public String welcome() {//Welcome page, non-rest
		return "Welcome to GraphQL-SPQR Demo app.";
	}
    
    @RequestMapping(value = "/graphql", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object index(@RequestBody Map<String, Object> request) {
        return graphQl.execute(request.get("query").toString()).getData();
    }
	
}
