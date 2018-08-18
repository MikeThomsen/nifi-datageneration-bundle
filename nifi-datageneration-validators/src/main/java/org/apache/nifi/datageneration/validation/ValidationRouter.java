package org.apache.nifi.datageneration.validation;

import org.apache.nifi.controller.AbstractControllerService;

import java.util.Map;

public class ValidationRouter extends AbstractControllerService implements TemplateOutputValidator {
    @Override
    public void validate(String input, Map<String, String> attributes) {

    }
}
