package org.apache.nifi.datageneration.templates;

import com.mitchellbosecke.pebble.loader.StringLoader;

import java.io.Reader;
import java.util.Map;

public class StringMapLoader extends StringLoader {
    private Map<String, String> templateCache;

    public StringMapLoader(Map<String, String> cache) {
        templateCache = cache;
    }

    @Override
    public Reader getReader(String templateName) {
        String val = templateCache.getOrDefault(templateName, templateName);
        return super.getReader(val);
    }
}
