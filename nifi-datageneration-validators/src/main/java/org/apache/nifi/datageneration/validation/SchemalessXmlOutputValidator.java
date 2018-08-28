package org.apache.nifi.datageneration.validation;

import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.processor.exception.ProcessException;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class SchemalessXmlOutputValidator extends AbstractControllerService implements TemplateOutputValidator {
    @Override
    public void validate(String input, Map<String, String> attributes) {
        try {
            DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(input.getBytes()));
        } catch (Exception e) {
            throw new ProcessException(e);
        }
    }
}
