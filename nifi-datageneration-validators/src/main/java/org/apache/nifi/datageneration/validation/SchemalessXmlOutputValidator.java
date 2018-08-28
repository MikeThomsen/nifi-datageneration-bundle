package org.apache.nifi.datageneration.validation;

import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.processor.exception.ProcessException;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.Map;

@Tags({"template", "output", "validator", "xml", "schemaless"})
@CapabilityDescription("A template output validator service that validates a template for syntatic correctness, not validity " +
        "as defined by a XML schema.")
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
