package org.apache.nifi.datageneration.transform;

import java.util.Map;

public interface PdfHandler {
    byte[] handle(byte[] input, Map<String, String> attributes) throws Exception;
}
