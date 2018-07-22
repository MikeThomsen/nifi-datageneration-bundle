package org.apache.nifi.datageneration.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PebbleTemplateRegistry extends AbstractControllerService implements TemplateRegistry {
    private Map<String, PebbleTemplate> cache = new ConcurrentHashMap<>();
    private volatile PebbleEngine engine;

    public void addTemplate(String name, String text) {
        PebbleTemplate template = engine.getTemplate(text);
        cache.put(name, template);
    }

    public String generateByName(byte[] model, String name) {
        PebbleTemplate template = cache.get(name);
        if (template == null) {
            throw new TemplateException(String.format("No registered template named \"%s\"", name));
        }

        try {
            Map<String, Object> modelMap = new ObjectMapper().readValue(model, Map.class);

            return evaluate(template, modelMap);
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    public String generateByText(byte[] model, String text) {
        try {
            Map<String, Object> modelMap = new ObjectMapper().readValue(model, Map.class);
            PebbleTemplate template = engine.getTemplate(text);

            return evaluate(template, modelMap);
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    private String evaluate(PebbleTemplate template, Map<String, Object> modelMap) throws IOException {
        StringWriter writer = new StringWriter();
        template.evaluate(writer, modelMap);
        writer.close();

        return writer.toString();
    }

    public void removeTemplate(String name) {
        cache.remove(name);
    }

    protected PropertyDescriptor getSupportedDynamicPropertyDescriptor(String propertyDescriptorName) {
        return new PropertyDescriptor.Builder()
            .name(propertyDescriptorName)
            .displayName(propertyDescriptorName)
            .dynamic(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    }

    public void onPropertyModified(PropertyDescriptor descriptor, String oldValue, String newValue) {
        if (descriptor.isDynamic()) {

        }
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        engine = new PebbleEngine.Builder()
            .loader(new StringLoader())
            .build();
    }
}
