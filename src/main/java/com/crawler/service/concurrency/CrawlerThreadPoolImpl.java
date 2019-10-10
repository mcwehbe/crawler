package com.crawler.service.concurrency;

import com.crawler.model.UrlGeneration;
import com.crawler.model.UrlListLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toList;

@Service
public class CrawlerThreadPoolImpl implements CrawlerThreadPool {

    private final int numberThreads;

    private final Executor executor;

    public CrawlerThreadPoolImpl(
            @Value("${thread.utilizationCPU}") final double utilizationCPU,
            @Value("${thread.computeTimeRatio}") final int computeTimeRatio) {
        ThreadPool threadPool = new ThreadPool(utilizationCPU, computeTimeRatio);
        executor = threadPool.getExecutor();
        numberThreads = threadPool.getNumberThreads();
    }

    @Override
    public List<UrlListLink> getUrlListLinks(
            List<UrlGeneration> urlGenerations,
            BiFunction<UrlGeneration, Queue<UrlGeneration>, UrlListLink> addLinksToQueue,
            Queue<UrlGeneration> queue) {

        return urlGenerations.stream()
                .map(urlGeneration -> CompletableFuture.supplyAsync(
                        () -> addLinksToQueue.apply(urlGeneration, queue), executor)
                )
                .collect(toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    @Override
    public int getNumberThreads() {
        return numberThreads;
    }
}
