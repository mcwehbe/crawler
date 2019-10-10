package com.crawler.service.http;

import com.crawler.util.StringUrl;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpParserServiceImpl implements HttpParserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParserServiceImpl.class);

    private static String LINK_SELECTOR = "a[href]";
    private static String URL_SELECTOR = "abs:href";

    private final HttpConnectionService httpConnectionService;

    @Autowired
    public HttpParserServiceImpl(HttpConnectionService httpConnectionService) {
        this.httpConnectionService = httpConnectionService;
    }

    @Override
    @Cacheable("getDistinctLinks")
    public List<String> getDistinctLinks(URL url) {
        return getLinkElements(url.toString()).stream()
                .map(link -> StringUrl.removeTrailingSlash(link.attr(URL_SELECTOR)))
                .distinct()
                .filter(link -> link.contains(url.getHost()))
                .collect(Collectors.toList());
    }

    @Override
    public Elements getLinkElements(String url) {
        try {
            return httpConnectionService
                    .connect(url)
                    .get()
                    .select(LINK_SELECTOR);
        } catch (IOException e) {
            LOGGER.error("Connection error with message={}", e.getMessage());
            return new Elements();
        }
    }
}
