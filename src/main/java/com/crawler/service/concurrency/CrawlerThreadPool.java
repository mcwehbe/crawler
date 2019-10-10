package com.crawler.service.concurrency;

import com.crawler.model.UrlGeneration;
import com.crawler.model.UrlListLink;

import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public interface CrawlerThreadPool {

    List<UrlListLink> getUrlListLinks(
            List<UrlGeneration> urlGenerations,
            BiFunction<UrlGeneration, Queue<UrlGeneration>, UrlListLink> addLinksToQueue,
            Queue<UrlGeneration> queue);

    int getNumberThreads();
}
