package com.crawler.service.http;

import org.jsoup.select.Elements;

import java.net.URL;
import java.util.List;

public interface HttpParserService {

    List<String> getDistinctLinks(URL url);

    Elements getLinkElements(String url);
}
