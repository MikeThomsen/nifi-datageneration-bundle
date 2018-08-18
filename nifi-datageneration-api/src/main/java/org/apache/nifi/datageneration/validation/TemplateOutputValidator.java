package org.apache.nifi.datageneration.validation;

import org.apache.nifi.controller.ControllerService;

public interface TemplateOutputValidator extends ControllerService {
    void validate(String input);
}
