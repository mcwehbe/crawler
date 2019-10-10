package com.crawler.service;

import com.crawler.model.UrlGeneration;
import com.crawler.model.UrlListLink;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Malek Wehbe
 */
public interface CrawlerService {
    Map<String, List<String>> crawl(String url, int maxNumberUrlTobeVisited);

    UrlListLink addLinksToQueue(UrlGeneration urlGenerationVisited, Queue<UrlGeneration> queue);
}
