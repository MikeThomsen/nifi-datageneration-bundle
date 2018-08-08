package org.apache.nifi.datageneration.transform;

import org.apache.nifi.controller.ControllerService;

import java.util.Map;

public interface TransformationService extends ControllerService {
    byte[] transform(byte[] input);
    byte[] transform(byte[] input, Map<String, String> attributes);
    String getMimeType();
}
