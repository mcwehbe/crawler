package com.crawler.service;

import com.crawler.model.UrlGeneration;
import com.crawler.service.concurrency.CrawlerThreadPool;
import com.crawler.util.StringUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UrlGenerationFinderServiceImpl implements UrlGenerationFinderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlGenerationFinderServiceImpl.class);

    private CrawlerThreadPool crawlerThreadPool;
    private int MaxUrlNumber;

    @Autowired
    public UrlGenerationFinderServiceImpl(final CrawlerThreadPool crawlerThreadPool) {
        this.crawlerThreadPool = crawlerThreadPool;
        MaxUrlNumber = this.crawlerThreadPool.getNumberThreads();
    }

    @Override
    public List<UrlGeneration> getUrlGeneration(
            Queue<UrlGeneration> queue,
            Map<String, List<String>> visitedUrl,
            int maxGenerationNumber) {

        int elementsRemoved = 0;
        List<UrlGeneration> urlGenerations = new LinkedList<>();
        Set<String> urlsToBeVisited = new HashSet<>();
        while (!queue.isEmpty() && elementsRemoved < MaxUrlNumber) {
            UrlGeneration urlGeneration = queue.remove();
            if (hasMaxGenerationNumberReached(maxGenerationNumber, urlGeneration)) {
                LOGGER.info("Max generation number {} has been reached", maxGenerationNumber);
                break;
            }
            String urlWithoutProtocol = StringUrl.removeProtocol(urlGeneration.getUrl());
            if (urlsToBeVisited.contains(urlWithoutProtocol) || (hasUrlBeenVisited(visitedUrl, urlWithoutProtocol))) {
                continue;
            }

            urlsToBeVisited.add(urlWithoutProtocol);
            urlGenerations.add(urlGeneration);
            elementsRemoved++;
        }

        return urlGenerations;
    }

    private boolean hasMaxGenerationNumberReached(int maxGenerationNumber, UrlGeneration urlGeneration) {
        return urlGeneration.getGeneration() > maxGenerationNumber;
    }

    private boolean hasUrlBeenVisited(Map<String, List<String>> visitedUrl, String urlWithoutProtocol) {
        return visitedUrl.containsKey(urlWithoutProtocol);
    }
}
