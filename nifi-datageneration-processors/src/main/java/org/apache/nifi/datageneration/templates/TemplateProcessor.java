package org.apache.nifi.datageneration.templates;

import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TemplateProcessor extends AbstractProcessor {
    static final PropertyDescriptor REGISTRY = new PropertyDescriptor.Builder()
        .name("template-registry")
        .displayName("Template Registry")
        .description("The template registry to use with this processor to generate data.")
        .identifiesControllerService(TemplateRegistry.class)
        .required(true)
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
        PROPERTIES = Collections.unmodifiableList(_temp);

        Set<Relationship> _rels = new HashSet<>();
        _rels.add(REL_SUCCESS);
        _rels.add(REL_FAILURE);
        _rels.add(REL_ORIGINAL);
        RELATIONSHIP_SET = Collections.unmodifiableSet(_rels);
    }

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

    }

    public void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {
        
    }
}
