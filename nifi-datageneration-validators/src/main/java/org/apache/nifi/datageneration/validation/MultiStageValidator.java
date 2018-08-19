package org.apache.nifi.datageneration.validation;

import org.apache.nifi.annotation.lifecycle.OnDisabled;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiStageValidator extends AbstractControllerService implements TemplateOutputValidator {
    @Override
    public PropertyDescriptor getSupportedDynamicPropertyDescriptor(String name) {
        return new PropertyDescriptor.Builder()
            .name(name)
            .displayName(name)
            .identifiesControllerService(TemplateOutputValidator.class)
            .required(true)
            .build();
    }

    private volatile List<TemplateOutputValidator> validators;

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
