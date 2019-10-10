package com.crawler.util;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class StringUrl {
    public static String removeProtocol(String url) {
        if(isBlank(url)){
            return url;
        }
        return url.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
    }

    public static String removeTrailingSlash(String url) {
        if(isBlank(url)){
            return url;
        }
        return url.replaceFirst("(/#|/.|#|/)$", "");
    }
}
