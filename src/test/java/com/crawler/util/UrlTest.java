package com.crawler.util;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
* @author Malek Wehbe
 */
class UrlTest {

    @Test
    void getHostName() throws Exception {
        assertEquals("test.com", new URL("http://test.com").getHost());
    }

    @Test
    void getPath() throws Exception {
        assertEquals("/page", new URL("http://test.com/page").getPath());
    }
    @Test
    void getStringUrl() throws Exception {
        assertEquals("http://test.com:8080/page", new URL("http://test.com:8080/page").toString());
    }
}