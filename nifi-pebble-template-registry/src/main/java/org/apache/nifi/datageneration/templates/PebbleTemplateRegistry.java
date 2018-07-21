package org.apache.nifi.datageneration.templates;

import org.apache.nifi.controller.AbstractControllerService;

public class PebbleTemplateRegistry extends AbstractControllerService implements TemplateRegistry {
    public void addTemplate(String name, String text) {

    }

    public String generateByName(byte[] model, String name) {
        return null;
    }

    public String generateByText(byte[] model, String text) {
        return null;
    }

    public void removeTemplate(String name) {

    }
}
