package com.crawler.model;

import java.util.Objects;

public final class UrlGeneration {
    private final String url;
    private final int generation;

    public UrlGeneration(String url, int generation) {
        this.url = url;
        this.generation = generation;
    }

    public String getUrl() {
        return url;
    }

    public int getGeneration() {
        return generation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlGeneration that = (UrlGeneration) o;
        return generation == that.generation &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, generation);
    }

    @Override
    public String toString() {
        return "UrlGeneration{" +
                "url='" + url + '\'' +
                ", generation=" + generation +
                '}';
    }
}
