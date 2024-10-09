package io.leangen.graphql.samples.dto;

import io.leangen.graphql.annotations.GraphQLInputField;
import io.leangen.graphql.annotations.types.GraphQLType;

public sealed interface FilterInput {
    @GraphQLType(description = "Filter for ranges of comparable types.")
    record RangeFilter<T extends Comparable<T>>(
        @GraphQLInputField(name = "start", description = "The start of the range, unbounded if null") T start,
        @GraphQLInputField(name = "startInclusive", description = "Whether start is inclusive", defaultValue = "true") boolean startInclusive,
        @GraphQLInputField(name = "end", description = "The end of range, unbounded if null") T end,
        @GraphQLInputField(name = "endInclusive", description = "Whether end is inclusive", defaultValue = "true") boolean endInclusive
    ) implements FilterInput {
        public RangeFilter(T start, boolean startInclusive, T end, boolean endInclusive) {
            this.start = start;
            this.startInclusive = startInclusive;
            this.end = end;
            this.endInclusive = endInclusive;
        }

        @Override
        public T start() {
            return start;
        }

        @Override
        public boolean startInclusive() {
            return startInclusive;
        }

        @Override
        public T end() {
            return end;
        }

        @Override
        public boolean endInclusive() {
            return endInclusive;
        }
    }
}
