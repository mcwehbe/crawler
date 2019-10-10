package com.crawler.service.http;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpConnectionServiceImpl implements HttpConnectionService {
    @Value("${connect.timout}")
    int timeout;

    @Override
    public Connection connect(String url) {
        Connection connect = HttpConnection.connect(url);
        connect.timeout(timeout);
        return connect;
    }
}
