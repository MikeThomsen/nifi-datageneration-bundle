package org.apache.nifi.datageneration.transform;

import java.util.Map;

public interface TransformationService {
    byte[] transform(byte[] input);
    byte[] transform(byte[] input, Map<String, String> attributes);
}
