package com.crawler.service;

import com.crawler.model.UrlGeneration;
import com.crawler.service.concurrency.CrawlerThreadPoolImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class UrlGenerationFinderServiceImplTest {

    private UrlGenerationFinderService urlGenerationFinderService;

    @Before
    public void setUp() {
        urlGenerationFinderService = new UrlGenerationFinderServiceImpl(
                new CrawlerThreadPoolImpl(0.5, 80)
        );
    }

    @Test
    public void getUrlGeneration() {
        //given
        Map<String, List<String>> visitedUrl = new LinkedHashMap<>();
        Queue<UrlGeneration> queue = new LinkedList<>();
        queue.add(new UrlGeneration("http://test.com", 1));
        queue.add(new UrlGeneration("http://test.com/page1", 2));
        queue.add(new UrlGeneration("http://test.com/page2", 2));
        queue.add(new UrlGeneration("http://test.com", 2));
        queue.add(new UrlGeneration("http://test.com/page3", 3));
        //when
        List<UrlGeneration> urlGenerations = urlGenerationFinderService.getUrlGeneration(queue, visitedUrl, 2);
        //then
        assertEquals(Arrays.asList(
                new UrlGeneration("http://test.com", 1),
                new UrlGeneration("http://test.com/page1", 2),
                new UrlGeneration("http://test.com/page2", 2)),
                urlGenerations);
    }
}