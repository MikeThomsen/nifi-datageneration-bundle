package org.apache.nifi.datageneration.transform;

import org.apache.nifi.controller.AbstractControllerService;

import java.util.Map;

public class PdfTransformer extends AbstractControllerService implements TransformationService {
    @Override
    public byte[] transform(byte[] input) {
        return new byte[0];
    }

    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) {
        return new byte[0];
    }
}
