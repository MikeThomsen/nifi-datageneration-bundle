package org.apache.nifi.datageneration.validation;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;

public abstract class DynamicValidator extends AbstractControllerService implements TemplateOutputValidator {
    @Override
    public PropertyDescriptor getSupportedDynamicPropertyDescriptor(String name) {
        return new PropertyDescriptor.Builder()
            .name(name)
            .displayName(name)
            .identifiesControllerService(TemplateOutputValidator.class)
            .required(true)
            .build();
    }
}
