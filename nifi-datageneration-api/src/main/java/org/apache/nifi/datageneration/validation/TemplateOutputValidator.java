package org.apache.nifi.datageneration.validation;

import org.apache.nifi.controller.ControllerService;

import java.util.Map;

public interface TemplateOutputValidator extends ControllerService {
    void validate(String input, Map<String, String> attributes);
}
