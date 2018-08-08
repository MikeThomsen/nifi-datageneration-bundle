package org.apache.nifi.datageneration.templates;

import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.datageneration.transform.TransformationService;
import org.apache.nifi.expression.ExpressionLanguageScope;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Tags({"template", "data", "generation"})
@CapabilityDescription("A processor for taking templates and building data from them. The processor itself does not implement " +
        "support for any particular template library or scheme, but instead requires a compatible controller service called a " +
        "TemplateRegistry.")
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@ReadsAttributes({
    @ReadsAttribute(attribute = TemplateProcessor.TEMPLATE_TEXT, description = "The raw text of the template."),
    @ReadsAttribute(attribute = TemplateProcessor.TEMPLATE_NAME, description = "The name of a template that has been registered as a dynamic property.")
})
public class TemplateProcessor extends AbstractProcessor {
    static final String TEMPLATE_NAME = "template.name";
    static final String TEMPLATE_TEXT = "template.text";
    static final String OUTPUT_MIME_TYPE = "out.mime.type";

    static final String DEFAULT_OUTPUT_MIME_TYPE = "text/text";

    static final PropertyDescriptor REGISTRY = new PropertyDescriptor.Builder()
        .name("template-registry")
        .displayName("Template Registry")
        .description("The template registry to use with this processor to generate data.")
        .identifiesControllerService(TemplateRegistry.class)
        .required(true)
        .build();

    static final PropertyDescriptor TRANSFORMER = new PropertyDescriptor.Builder()
        .name("transformer-service")
        .displayName("Output Transformer")
        .description("A controller service that can transform the output of the template processing.")
        .identifiesControllerService(TransformationService.class)
        .required(false)
        .build();

    static final PropertyDescriptor MIME_TYPE = new PropertyDescriptor.Builder()
        .name("mime-type")
        .displayName("MIME Type")
        .description(String.format("The MIME Type to set on the output files. Default is %s", DEFAULT_OUTPUT_MIME_TYPE))
        .required(true)
        .defaultValue(DEFAULT_OUTPUT_MIME_TYPE)
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .expressionLanguageSupported(ExpressionLanguageScope.FLOWFILE_ATTRIBUTES)
        .build();

    static final Relationship REL_FAILURE = new Relationship.Builder()
        .name("failure")
        .description("When a template cannot be built, the input flowfile goes to this relationship.")
        .build();
    static final Relationship REL_SUCCESS = new Relationship.Builder()
        .name("success")
        .description("When a template is successfully built, it goes to this relationship.")
        .build();
    static final Relationship REL_ORIGINAL = new Relationship.Builder()
        .name("original")
        .description("The input flowfile goes to this relationship if the template builds successfully.")
        .build();

    static final List<PropertyDescriptor> PROPERTIES;
    static final Set<Relationship> RELATIONSHIP_SET;

    static {
        List<PropertyDescriptor> _temp = new ArrayList<>();
        _temp.add(REGISTRY);
        _temp.add(TRANSFORMER);
        _temp.add(MIME_TYPE);
        PROPERTIES = Collections.unmodifiableList(_temp);

        Set<Relationship> _rels = new HashSet<>();
        _rels.add(REL_SUCCESS);
        _rels.add(REL_FAILURE);
        _rels.add(REL_ORIGINAL);
        RELATIONSHIP_SET = Collections.unmodifiableSet(_rels);
    }

    private volatile TemplateRegistry registry;
    private volatile TransformationService transformationService;

    @Override
    public Set<Relationship> getRelationships() {
        return RELATIONSHIP_SET;
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return PROPERTIES;
    }

    @OnScheduled
    public void onScheduled(ProcessContext context) {
        registry = context.getProperty(REGISTRY).asControllerService(TemplateRegistry.class);
        transformationService = context.getProperty(TRANSFORMER).isSet()
            ? context.getProperty(TRANSFORMER).asControllerService(TransformationService.class)
            : null;
    }

    public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {
        FlowFile input = session.get();
        if (input == null) {
            return;
        }

        final String text = input.getAttribute(TEMPLATE_TEXT);
        final String name = input.getAttribute(TEMPLATE_NAME);
        final String outMime = context.getProperty(MIME_TYPE).evaluateAttributeExpressions(input).getValue();
        FlowFile outFF = null;
        try {
            byte[] model = getModel(input, session);
            final String output;
            if (text != null && text.trim().length() > 0) {
                output = registry.generateByText(model, text);
            } else if (name != null && name.trim().length() > 0) {
                output = registry.generateByName(model, name);
            } else {
                throw new ProcessException(String.format("Input flowfile did not have either %s or %s defined as attributes.", TEMPLATE_NAME, TEMPLATE_TEXT));
            }

            byte[] content = getOutputFlowFileContent(output, input.getAttributes());

            outFF = session.write(session.create(input), out -> out.write(content));
            outFF = session.putAttribute(outFF, OUTPUT_MIME_TYPE, outMime);
            session.transfer(outFF, REL_SUCCESS);
            session.transfer(input, REL_ORIGINAL);
        } catch (Exception ex) {
            getLogger().error("Could not process flowfile", ex);
            session.transfer(input, REL_FAILURE);
            if (outFF != null) {
                session.remove(outFF);
            }
        }
    }

    private byte[] getOutputFlowFileContent(String output, Map<String, String> attributes) {
        byte[] retVal = output.getBytes();
        if (transformationService != null) {
            retVal = transformationService.transform(retVal, attributes);
        }

        return retVal;
    }

    private byte[] getModel(FlowFile input, ProcessSession session) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        session.exportTo(input, out);
        out.close();

        return out.toByteArray();
    }
}
