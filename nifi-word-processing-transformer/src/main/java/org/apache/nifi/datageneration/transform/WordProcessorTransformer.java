package org.apache.nifi.datageneration.transform;

import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.AllowableValue;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.datageneration.transform.docx.DocxHtmlHandler;
import org.apache.nifi.datageneration.transform.docx.DocxRawTextHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordProcessorTransformer extends AbstractControllerService implements TransformationService {
    static final AllowableValue DOCX_RAW_TEXT = new AllowableValue("docx_text", "DOCX from Plain Text", "Converts each paragraph " +
            "into a PDF paragraph and removes empty lines.");
    static final AllowableValue DOCX_HTML = new AllowableValue("docx_html", "DOCX from HTML", "Converts HTML to DOCX");

    static final PropertyDescriptor HANDLER = new PropertyDescriptor.Builder()
            .name("handler")
            .displayName("Handler")
            .allowableValues(DOCX_RAW_TEXT, DOCX_HTML)
            .defaultValue(DOCX_RAW_TEXT.getValue())
            .description("Choose a handler for parsing the output of the template processor.")
            .required(true)
            .build();
    static final Map<String, WordProcessorHandler> HANDLERS;

    static {
        Map<String, WordProcessorHandler> _h = new HashMap<>();
        _h.put(DOCX_RAW_TEXT.getValue(), new DocxRawTextHandler());
        _h.put(DOCX_HTML.getValue(), new DocxHtmlHandler());
        HANDLERS = Collections.unmodifiableMap(_h);
    }

    private volatile WordProcessorHandler wpHandler;

    @Override
    public List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return Arrays.asList(HANDLER);
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        String handlerChoice = context.getProperty(HANDLER).getValue();
        wpHandler = HANDLERS.get(handlerChoice);
    }
    @Override
    public byte[] transform(byte[] input) {
        return transform(input, new HashMap<>());
    }

    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) {
        return new byte[0];
    }

    @Override
    public String getMimeType() {
        return null;
    }
}
