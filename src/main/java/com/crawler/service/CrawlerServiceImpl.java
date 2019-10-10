package com.crawler.service;

import com.crawler.model.UrlGeneration;
import com.crawler.model.UrlListLink;
import com.crawler.service.concurrency.CrawlerThreadPool;
import com.crawler.service.http.HttpParserService;
import com.crawler.util.StringUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author Malek Wehbe
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerServiceImpl.class);
    private static final int FIRST_GENERATION = 1;

    private HttpParserService httpParserService;
    private UrlGenerationFinderService urlGenerationFinderService;
    private CrawlerThreadPool crawlerThreadPool;

    @Autowired
    public CrawlerServiceImpl(
            final HttpParserService httpParserService,
            final UrlGenerationFinderService urlGenerationFinderService,
            final CrawlerThreadPool crawlerThreadPool) {
        this.httpParserService = httpParserService;
        this.urlGenerationFinderService = urlGenerationFinderService;
        this.crawlerThreadPool = crawlerThreadPool;
    }

    @Override
    public Map<String, List<String>> crawl(String url, int maxGenerationNumber) {
        Map<String, List<String>> visitedUrl = new LinkedHashMap<>();
        Queue<UrlGeneration> queue = new LinkedList<>();
        queue.add(new UrlGeneration(url, FIRST_GENERATION));

        while (!queue.isEmpty()) {
            List<UrlGeneration> urlGenerations = urlGenerationFinderService.getUrlGeneration(
                    queue,
                    visitedUrl,
                    maxGenerationNumber);

            if (urlGenerations.isEmpty()) {
                break;
            }

            crawlerThreadPool.getUrlListLinks(urlGenerations, this::addLinksToQueue, queue)
                    .stream()
                    .forEach(urlListLink -> {
                        visitedUrl.put(StringUrl.removeProtocol(urlListLink.getUrl()), urlListLink.getLinks());
                        LOGGER.info(urlListLink.getUrl());
                    });
        }

        return visitedUrl;
    }

    @Override
    public UrlListLink addLinksToQueue(UrlGeneration urlGeneration, Queue<UrlGeneration> queue) {
        List<String> urls = new LinkedList<>();
        try {
            httpParserService.getDistinctLinks(new URL(urlGeneration.getUrl())).stream()
                    .forEach(urlFound -> {
                        queue.add(new UrlGeneration(urlFound, getNextGeneration(urlGeneration)));
                        urls.add(urlFound);
                    });
        } catch (MalformedURLException e) {
            LOGGER.error("MalformedURLException with url={} and Exception message={}", urlGeneration.getGeneration(), e.getMessage());
        } finally {
            return new UrlListLink(urlGeneration.getUrl(), urls);
        }
    }

    private int getNextGeneration(UrlGeneration urlGeneration) {
        return urlGeneration.getGeneration() + FIRST_GENERATION;
    }
}
