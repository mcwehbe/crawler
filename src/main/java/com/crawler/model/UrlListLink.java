package com.crawler.model;

import java.util.List;
import java.util.Objects;

public class UrlListLink {
    private final String url;
    private final List<String> links;

    public UrlListLink(String url, List<String> links) {
        this.url = url;
        this.links = links;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlListLink that = (UrlListLink) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, links);
    }

    @Override
    public String toString() {
        return "UrlListLink{" +
                "url='" + url + '\'' +
                ", links=" + links +
                '}';
    }
}
