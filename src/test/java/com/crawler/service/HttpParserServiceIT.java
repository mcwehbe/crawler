package com.crawler.service;

import com.crawler.service.http.HttpParserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock(port = 0)
public class HttpParserServiceIT {

    @Autowired
    private HttpParserService httpParserService;

    @Value("${wiremock.server.port}")
    private Integer port;

    @Ignore
    @Test
    public void getEmptyLinks() throws Exception {
        //given
        URL url = new URL("http://localhost:" + port + "/simple");
        //when
        List<String> links = httpParserService.getDistinctLinks(url);
        //then
        assertEquals(new ArrayList(), links);
    }

    @Ignore
    @Test
    public void geLinks() throws Exception {
        //given
        URL url = new URL("http://localhost:" + port + "/links");
        //when
        List<String> links = httpParserService.getDistinctLinks(url);
        //then
        assertEquals(Arrays.asList("http://localhost", "https://localhost/page1"), links);
    }
}