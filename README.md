# GraphQL-SPQR Spring MVC Samples

[![Build Status](https://travis-ci.org/leangen/graphql-spqr-samples.svg?branch=master)](https://travis-ci.org/leangen/graphql-spqr-samples)
[![Join the chat at https://gitter.im/leangen/graphql-spqr](https://badges.gitter.im/leangen/graphql-spqr.svg)](https://gitter.im/leangen/graphql-spqr?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Simplistic Spring Boot application for demoing GraphQl SPQR lib's capabilities.
This is mostly used by us for doing live demo's in our talks, not as documentation.

Package with maven, run the jar, play around.

Out of the box it runs on port 7777

Graphiql tool is mapped to root (e.g. http://localhost:7777/)

API is exposed on /graphql

Example queries can be found in javadoc for query methods

For an elaborate introspection query you might want to use:
```graphql
query IntrospectionQuery {
    __schema {
      queryType { name }
      mutationType { name }
      subscriptionType { name }
      types {
        ...FullType
      }
      directives {
        name
        description
        locations
        args {
          ...InputValue
        }
      }
    }
  }
  fragment FullType on __Type {
    kind
    name
    description
    fields(includeDeprecated: true) {
      name
      description
      args {
        ...InputValue
      }
      type {
        ...TypeRef
      }
      isDeprecated
      deprecationReason
    }
    inputFields {
      ...InputValue
    }
    interfaces {
      ...TypeRef
    }
    enumValues(includeDeprecated: true) {
      name
      description
      isDeprecated
      deprecationReason
    }
    possibleTypes {
      ...TypeRef
    }
  }
  fragment InputValue on __InputValue {
    name
    description
    type { ...TypeRef }
    defaultValue
  }
  fragment TypeRef on __Type {
    kind
    name
    ofType {
      kind
      name
      ofType {
        kind
        name
        ofType {
          kind
          name
          ofType {
            kind
            name
            ofType {
              kind
              name
              ofType {
                kind
                name
                ofType {
                  kind
                  name
                }
              }
            }
          }
        }
      }
    }
  }
```