package com.crawler.util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUrlTest {

    @Test
    public void removeProtocol() {
        assertEquals("test.com/page", StringUrl.removeProtocol("http://test.com/page"));
        assertEquals("test.com/page", StringUrl.removeProtocol("https://test.com/page"));
        assertEquals("test.com/page", StringUrl.removeProtocol("http://www.test.com/page"));
        assertEquals("test.com/page", StringUrl.removeProtocol("www.test.com/page"));
        assertEquals("test.com/page", StringUrl.removeProtocol("test.com/page"));
    }

    @Test
    public void removeTrailingSlash() {
        assertEquals("http://test.com/page", StringUrl.removeTrailingSlash("http://test.com/page/"));
        assertEquals("http://test.com/page", StringUrl.removeTrailingSlash("http://test.com/page/."));
        assertEquals("http://test.com/page", StringUrl.removeTrailingSlash("http://test.com/page#"));
        assertEquals("http://test.com/page", StringUrl.removeTrailingSlash("http://test.com/page/#"));
    }
}