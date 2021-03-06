package org.apache.nifi.datageneration.transform;

import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.AllowableValue;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.exception.ProcessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfTransformer extends AbstractControllerService implements TransformationService {
    public static final String MIME_TYPE = "application/pdf";

    static final AllowableValue RAW_TEXT = new AllowableValue("text", "Plain Text", "Converts each paragraph " +
            "into a PDF paragraph and removes empty lines.");
    static final AllowableValue HTML = new AllowableValue("html", "HTML", "Converts HTML to PDF");
    static final PropertyDescriptor HANDLER = new PropertyDescriptor.Builder()
        .name("handler")
        .displayName("Handler")
        .allowableValues(RAW_TEXT, HTML)
        .defaultValue(RAW_TEXT.getValue())
        .description("Choose a handler for parsing the output of the template processor.")
        .required(true)
        .build();

    static final List<PropertyDescriptor> DESCRIPTORS;
    static final Map<String, PdfHandler> HANDLERS;

    static {
        List<PropertyDescriptor> _temp = new ArrayList<>();
        _temp.add(HANDLER);
        DESCRIPTORS = Collections.unmodifiableList(_temp);

        Map<String, PdfHandler> _h = new HashMap<>();
        _h.put(RAW_TEXT.getValue(), new RawTextHandler());
        _h.put(HTML.getValue(), new HtmlHandler());
        HANDLERS = Collections.unmodifiableMap(_h);
    }

    private volatile PdfHandler pdfHandler;

    @Override
    public List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return DESCRIPTORS;
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        String handler = context.getProperty(HANDLER).getValue();
        pdfHandler = HANDLERS.get(handler);
    }

    @Override
    public byte[] transform(byte[] input) {
        return transform(input, null);
    }

    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) {
        try {
            return pdfHandler.handle(input, attributes);
        } catch (Exception e) {
            throw new ProcessException(e);
        }
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }
}
