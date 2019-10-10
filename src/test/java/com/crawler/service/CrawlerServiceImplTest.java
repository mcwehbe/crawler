package com.crawler.service;

import com.crawler.service.concurrency.CrawlerThreadPool;
import com.crawler.service.concurrency.CrawlerThreadPoolImpl;
import com.crawler.service.http.HttpParserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.times;

public class CrawlerServiceImplTest {
    private CrawlerService crawlerService;
    @Mock
    private HttpParserService httpParserService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        CrawlerThreadPool crawlerThreadPool = new CrawlerThreadPoolImpl(0.5, 80);
        crawlerService = new CrawlerServiceImpl(
                httpParserService,
                new UrlGenerationFinderServiceImpl(crawlerThreadPool),
                crawlerThreadPool);
    }

    @Test
    public void crawl() throws Exception {
        //given
        given(httpParserService.getDistinctLinks(new URL("http://test.com")))
                .willReturn(Arrays.asList("http://test.com/page1", "http://test.com/page2"));
        given(httpParserService.getDistinctLinks(new URL("http://test.com/page2")))
                .willReturn(Arrays.asList("http://test.com/page3", "http://test.com/page1"));

        // when
        Map<String, List<String>> map = crawlerService.crawl("http://test.com", 3);
        //then
        verify(httpParserService, times(1)).getDistinctLinks(new URL("http://test.com"));
        verify(httpParserService, times(1)).getDistinctLinks(new URL("http://test.com/page1"));
        verify(httpParserService, times(1)).getDistinctLinks(new URL("http://test.com/page2"));
        verify(httpParserService, times(1)).getDistinctLinks(new URL("http://test.com/page3"));

        assertEquals(new LinkedHashMap<String, List<String>>() {{
            put("test.com", Arrays.asList("http://test.com/page1", "http://test.com/page2"));
            put("test.com/page1", new LinkedList<>());
            put("test.com/page2", Arrays.asList("http://test.com/page3", "http://test.com/page1"));
            put("test.com/page3", new LinkedList<>());
        }}, map);
    }

    @Test
    public void crawlTestNumberGenerationHasBeenReached() throws Exception {
        //given
        given(httpParserService.getDistinctLinks(new URL("http://test.com")))
                .willReturn(Arrays.asList("http://test.com/page1", "http://test.com/page2"));
        given(httpParserService.getDistinctLinks(new URL("http://test.com/page2")))
                .willReturn(Arrays.asList("http://test.com/page3", "http://test.com/page1"));
        //when
        Map<String, List<String>> map = crawlerService.crawl("http://test.com", 2);
        //then
        assertEquals(map, new LinkedHashMap<String, List<String>>() {{
            put("test.com", Arrays.asList("http://test.com/page1", "http://test.com/page2"));
            put("test.com/page1", new LinkedList<>());
            put("test.com/page2", Arrays.asList("http://test.com/page3", "http://test.com/page1"));
        }});
    }
}