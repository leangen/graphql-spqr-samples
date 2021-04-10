package io.leangen.graphql.samples.config;

import io.leangen.graphql.samples.dto.Project;
import io.leangen.graphql.spqr.spring.autoconfigure.DataLoaderRegistryFactory;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataLoaderFactory implements DataLoaderRegistryFactory {

    private static final BatchLoader<Project, Long> fundingBatchLoader = DataLoaderFactory::fundings;

    @Override
    public DataLoaderRegistry createDataLoaderRegistry() {
        DataLoader<Project, Long> fundingLoader = new DataLoader<>(fundingBatchLoader);
        DataLoaderRegistry loaders = new DataLoaderRegistry();
        loaders.register("funding", fundingLoader);
        return loaders;
    }

    //Simulates a call to an external API to fetch the current funding for multiple projects.
    private static CompletableFuture<List<Long>> fundings(List<Project> projects) {
        return CompletableFuture.completedFuture(
                IntStream.range(0, projects.size())
                        .mapToObj(i -> ThreadLocalRandom.current().nextLong(1000) * 1000)
                        .collect(Collectors.toList())
        );
    }
}
