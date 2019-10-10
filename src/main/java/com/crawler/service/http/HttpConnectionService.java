package com.crawler.service.http;

import org.jsoup.Connection;

public interface HttpConnectionService {
    Connection connect(String url);
}
