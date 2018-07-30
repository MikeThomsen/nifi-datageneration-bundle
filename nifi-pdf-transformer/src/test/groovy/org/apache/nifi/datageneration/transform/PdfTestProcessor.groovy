package org.apache.nifi.datageneration.transform

import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.processor.AbstractProcessor
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.exception.ProcessException

class PdfTestProcessor extends AbstractProcessor {
    static final PropertyDescriptor TRANSFORMER = new PropertyDescriptor.Builder()
        .name("transformer")
        .identifiesControllerService(TransformationService.class)
        .required(true)
        .build()

    @Override
    List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        [ TRANSFORMER ]
    }

    @Override
    void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {

    }
}
