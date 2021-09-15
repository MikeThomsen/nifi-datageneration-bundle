package org.apache.nifi.datageneration.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.ValidationContext;
import org.apache.nifi.components.ValidationResult;
import org.apache.nifi.components.Validator;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CapabilityDescription("A Template Registry implementation that provides support for the Pebble template library. Extensions to " +
        "Pebble can be added using the provided configuration properties for defining JAR locations and classes to load. ")
@Tags({"pebble", "template", "data", "generation"})
public class PebbleTemplateRegistry extends AbstractControllerService implements TemplateRegistry {
    private Map<String, PebbleTemplate> cache = new ConcurrentHashMap<>();
    private Map<String, String> rawCache = new ConcurrentHashMap<>();
    private volatile PebbleEngine engine;

    static final PropertyDescriptor EXTENSION_JARS = new PropertyDescriptor.Builder()
        .name("extension")
        .displayName("Extension JARs")
        .description("A comma-separated list of Java JAR files to be loaded. This has no practical effect unless combined " +
                "with the Extension Classes property which provides a list of particular classes to use as extensions for the " +
                "parsing engine.")
        .dynamicallyModifiesClasspath(true)
        .defaultValue("")
        .addValidator(Validator.VALID)
        .required(false)
        .build();

    static final PropertyDescriptor EXTENSION_CLASSES = new PropertyDescriptor.Builder()
        .name("extension-classes")
        .displayName("Extension Classes")
        .addValidator(Validator.VALID)
        .defaultValue("")
        .description("A comma-separated list of fully qualified Java class names that correspond to classes that implement " +
                "Pebble's Extension interface. This configuration property has no effect unless a value for the Extension JAR field is " +
                "also provided.")
        .required(false)
        .build();

    @Override
    public List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return Arrays.asList(EXTENSION_JARS, EXTENSION_CLASSES);
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
        if (descriptor.isDynamic() && newValue != null && !newValue.isEmpty()) {
            addTemplate(descriptor.getName(), newValue);
        }
    }

    @Override
    protected Collection<ValidationResult> customValidate(ValidationContext validationContext) {
        List<ValidationResult> retVal = new ArrayList<>();

        boolean jarsIsSet = !StringUtils.isEmpty(validationContext.getProperty(EXTENSION_JARS).getValue());
        boolean clzIsSet  = !StringUtils.isEmpty(validationContext.getProperty(EXTENSION_CLASSES).getValue());

        if (jarsIsSet && clzIsSet) {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                String[] classes = validationContext.getProperty(EXTENSION_CLASSES).getValue().split(",[\\s]*");
                for (String clz : classes) {
                    Class.forName(clz, true, loader);
                }
            } catch (Exception ex) {
                throw new ProcessException("Failed initialization due to bad extension configuration.", ex);
            }
        }

        return retVal;
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        PebbleEngine.Builder builder = new PebbleEngine.Builder()
                .loader(new StringMapLoader(rawCache));

        String path = context.getProperty(EXTENSION_JARS).getValue();
        String classList = context.getProperty(EXTENSION_CLASSES).getValue();
        if (!path.isEmpty() && !classList.isEmpty()) {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                String[] classes = context.getProperty(EXTENSION_CLASSES).getValue().split(",[\\s]*");
                for (String cls : classes) {
                    Class clz = Class.forName(cls, true, loader);
                    builder = builder.extension((Extension) clz.newInstance());
                }
            } catch (Exception e) {
                throw new ProcessException(e);
            }
        }

        engine = builder.build();

        cache.clear();
        rawCache.clear();
        context.getProperties().keySet().forEach(propertyDescriptor -> {
            if (propertyDescriptor.isDynamic()) {
                addTemplate(propertyDescriptor.getName(), context.getProperty(propertyDescriptor).getValue());
            }
        });
    }
}
