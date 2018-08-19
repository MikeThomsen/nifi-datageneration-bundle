package org.apache.nifi.datageneration.validation;

import org.apache.nifi.annotation.lifecycle.OnDisabled;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.exception.ProcessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiStageValidator extends DynamicValidator implements TemplateOutputValidator {
    private volatile List<TemplateOutputValidator> validators;
    public static final Pattern DYNAMIC_NAME_VALIDATOR = Pattern.compile("^([\\d]+)\\.");

    @Override
    public PropertyDescriptor getSupportedDynamicPropertyDescriptor(String name) {
        Matcher matcher = DYNAMIC_NAME_VALIDATOR.matcher(name);
        if (matcher.matches()) {
            return super.getSupportedDynamicPropertyDescriptor(name);
        } else {
            throw new ProcessException("Property name patterns should start with a number and a period. Ex. \"1.firstStep\"");
        }
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        List<TemplateOutputValidator> _temp = new ArrayList<>();
        for (PropertyDescriptor desc : context.getProperties().keySet()) {
            if (desc.isDynamic()) {
                _temp.add(context.getProperty(desc).asControllerService(TemplateOutputValidator.class));
            }
        }
        validators = _temp;
    }

    @OnDisabled
    public void onDisabled() {
        validators = null;
    }

    @Override
    public void validate(String input, Map<String, String> attributes) {
        for (TemplateOutputValidator validator : validators) {
            validator.validate(input, attributes);
        }
    }
}
