package org.apache.nifi.datageneration.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.Validator;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PebbleTemplateRegistry extends AbstractControllerService implements TemplateRegistry {
    private Map<String, PebbleTemplate> cache = new ConcurrentHashMap<>();
    private Map<String, String> rawCache = new ConcurrentHashMap<>();
    private volatile PebbleEngine engine;

    static final PropertyDescriptor EXTENSION = new PropertyDescriptor.Builder()
        .name("extension")
        .displayName("Extension")
        .dynamicallyModifiesClasspath(true)
        .addValidator(Validator.VALID)
        .required(false)
        .build();

    @Override
    public List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return Arrays.asList(EXTENSION);
    }

    public void addTemplate(String name, String text) {
        PebbleTemplate template = engine.getTemplate(text);
        cache.put(name, template);
        rawCache.put(name, text);
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
        rawCache.remove(name);
    }

    protected PropertyDescriptor getSupportedDynamicPropertyDescriptor(String propertyDescriptorName) {
        return new PropertyDescriptor.Builder()
            .name(propertyDescriptorName)
            .displayName(propertyDescriptorName)
            .dynamic(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    }

    @Override
    public void onPropertyModified(PropertyDescriptor descriptor, String oldValue, String newValue) {
        if (descriptor.isDynamic()) {

        }
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
//        engine = new PebbleEngine.Builder()
//            .loader(new StringMapLoader(rawCache))
//            .build();
        PebbleEngine.Builder builder = new PebbleEngine.Builder()
                .loader(new StringMapLoader(rawCache));

        String path = context.getProperty(EXTENSION).getValue();
        if (!path.isEmpty()) {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Class clz = Class.forName("org.apache.nifi.datageneration.templates.TestExtension", true, loader);
                builder.extension((Extension) clz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        engine = builder.build();
    }
}
