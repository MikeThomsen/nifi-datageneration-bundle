package org.apache.nifi.datageneration.transform.docx;

import org.apache.nifi.datageneration.transform.WordProcessorHandler;

import java.util.Map;

public class DocxHtmlHandler implements WordProcessorHandler {
    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) throws Exception {
        return new byte[0];
    }
}
