package io.leangen.graphql.samples.dto;

import io.leangen.graphql.annotations.GraphQLInputField;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType(description = "Filter for a field. Only one filter can be defined in the input argument.")
public record Filter(
    @GraphQLInputField(description = "Field ID to apply the filter to.")
    @GraphQLNonNull
    String fieldId,

    @GraphQLInputField(description = "Should filter be applied negated.", defaultValue = "false")
    boolean isNegated,

    @GraphQLInputField(description = "Filter for a datetime field.")
    FilterInput.RangeFilter<Long> dateTimeRange,

    @GraphQLInputField(description = "Range filter for an int field.")
    FilterInput.RangeFilter<Long> longRange
) {

    // If I remove this, it starts working
    public Filter {
    }

}
