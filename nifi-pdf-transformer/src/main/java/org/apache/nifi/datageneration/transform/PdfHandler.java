package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Document;

import java.util.Map;

public interface PdfHandler {
    void handle(Document document, byte[] model, Map<String, String> attributes) throws Exception;
}
