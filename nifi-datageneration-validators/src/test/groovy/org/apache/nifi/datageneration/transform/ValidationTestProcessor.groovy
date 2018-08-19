package org.apache.nifi.datageneration.transform

import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.datageneration.validation.TemplateOutputValidator
import org.apache.nifi.processor.AbstractProcessor
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.exception.ProcessException

class ValidationTestProcessor extends AbstractProcessor {
    static final PropertyDescriptor VALIDATOR = new PropertyDescriptor.Builder()
        .name("validator")
        .identifiesControllerService(TemplateOutputValidator.class)
        .required(true)
        .build()

    @Override
    List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        [ VALIDATOR ]
    }

    @Override
    void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {

    }
}
