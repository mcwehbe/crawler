package com.crawler.service;

import com.crawler.model.UrlGeneration;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface UrlGenerationFinderService {
    List<UrlGeneration> getUrlGeneration(Queue<UrlGeneration> queue, Map<String, List<String>> visitedUrl, int maxGenerationNumber);
}
