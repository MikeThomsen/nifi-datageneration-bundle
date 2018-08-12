package org.apache.nifi.datageneration.transform;

import java.util.Map;

public interface WordProcessorHandler {
    byte[] transform(byte[] input, Map<String, String> attributes) throws Exception;
}
