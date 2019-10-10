package com.crawler.command;

import com.crawler.service.CrawlerService;
import com.crawler.util.StringUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.*;

@ShellComponent
public class Command {
    private CrawlerService crawlerService;
    private WriterServiceImpl writerService;
    final private static String NEW_LINE = "####################\n";

    @Autowired
    public Command(final CrawlerService crawlerService, final WriterServiceImpl writerService) {

        this.crawlerService = crawlerService;
        this.writerService = writerService;
    }

    @ShellMethod("Crawler limited to one domain")
    public void crawl(@ShellOption(defaultValue = "https://test.com") String url,
                      @ShellOption(defaultValue = "2") int maxGenerationNumberToVisit) throws IOException {
        System.out.println("Beginning...");
        Map<String, List<String>> map = crawlerService.crawl(url, maxGenerationNumberToVisit);
        displayUrlByGeneration(map, StringUrl.removeProtocol(url));
        System.out.println("...End");
    }


    private void displayUrlByGeneration(Map<String, List<String>> map, String urlWithoutProtocol) throws IOException {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        String filename = String.format("files/crawler-%s-%s", urlWithoutProtocol, LocalDate.now());
        RandomAccessFile stream = new RandomAccessFile(filename, "rw");

        queue.add(urlWithoutProtocol);
        while (!queue.isEmpty()) {
            String urlToBeDisplay = queue.remove();
            visited.add(StringUrl.removeProtocol(urlToBeDisplay));
            List<String> list = map.get(urlToBeDisplay);

            if (list == null || list.isEmpty()) {
                continue;
            }
            writeTitleUrl(stream, urlToBeDisplay);

            for (String url : list) {
                writerService.write(stream, String.format("%s\n", url));
                String urlToVisit = StringUrl.removeProtocol(url);
                if (!visited.contains(urlToVisit)) {
                    visited.add(urlToVisit);
                    queue.add(urlToVisit);
                }
            }
            writeFooterUrl(stream);
        }
        writerService.close(stream);
    }

    private void writeFooterUrl(RandomAccessFile stream) throws IOException {
        writerService.write(stream, NEW_LINE);
    }

    private void writeTitleUrl(RandomAccessFile stream, String urlToBeDisplay) throws IOException {
        writerService.write(stream, NEW_LINE);
        writerService.write(stream, String.format("url %s:\n", urlToBeDisplay));
        writerService.write(stream, NEW_LINE);
    }
}
