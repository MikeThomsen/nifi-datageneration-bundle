package org.apache.nifi.datageneration.templates

import org.apache.nifi.controller.AbstractControllerService

class MockRegistry extends AbstractControllerService implements TemplateRegistry {
    boolean doFailure = false

    private String returnSomething() {
        if (doFailure) {
            throw new RuntimeException("Forced failure!")
        } else {
            return "Something passed through"
        }
    }

    @Override
    void addTemplate(String name, String text) {

    }

    @Override
    String generateByName(byte[] model, String name) {
        returnSomething()
    }

    @Override
    String generateByText(byte[] model, String text) {
        returnSomething()
    }

    @Override
    void removeTemplate(String name) {

    }
}