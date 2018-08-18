package org.apache.nifi.datageneration.validation;

import org.apache.nifi.annotation.lifecycle.OnDisabled;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.ValidationContext;
import org.apache.nifi.components.ValidationResult;
import org.apache.nifi.components.Validator;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.datageneration.templates.TemplateException;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class XmlSchemaOutputValidator extends AbstractControllerService implements TemplateOutputValidator {
    public static final PropertyDescriptor SCHEMA_LOCATION = new PropertyDescriptor.Builder()
        .name("schema-location")
        .displayName("Schema Location")
        .description("The location on the file system where the XML schema is located.")
        .addValidator(StandardValidators.FILE_EXISTS_VALIDATOR)
        .required(false)
        .build();

    public static final PropertyDescriptor SCHEMA_BODY = new PropertyDescriptor.Builder()
        .name("schema-body")
        .displayName("Schema Body")
        .description("The full text of the XML schema to be used for validation")
        .addValidator(Validator.VALID)
        .required(false)
        .build();

    @Override
    public Collection<ValidationResult> customValidate(ValidationContext context) {
        List<ValidationResult> problems = new ArrayList<>();

        boolean location = context.getProperty(SCHEMA_LOCATION).isSet();
        boolean body     = context.getProperty(SCHEMA_BODY).isSet();

        if (location && body) {
            problems.add(new ValidationResult.Builder().valid(false).explanation("Schema Location and Schema Body cannot both be set.").build());
        } else if (!location && !body) {
            problems.add(new ValidationResult.Builder().valid(false).explanation("Schema Location or Schema Body must be set.").build());
        }

        return problems;
    }

    private volatile javax.xml.validation.Validator validator;

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        Source source = null;
        if (context.getProperty(SCHEMA_BODY).isSet()) {
            String body = context.getProperty(SCHEMA_BODY).getValue();
            source = new StreamSource(new StringReader(body));
        } else if (context.getProperty(SCHEMA_LOCATION).isSet()) {
            source = new StreamSource(new File(context.getProperty(SCHEMA_LOCATION).getValue()));
        }

        if (source != null) {
            try {
                SchemaFactory schemaFactory = SchemaFactory
                        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = schemaFactory.newSchema(source);
                validator = schema.newValidator();
            } catch (Exception ex) {
                throw new ProcessException(ex);
            }
        }
    }

    @OnDisabled
    public void onDisabled() {
        validator = null;
    }

    @Override
    public void validate(String input, Map<String, String> attributes) {
        try {
            validator.validate(new StreamSource(new StringReader(input)));
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
