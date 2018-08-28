package org.apache.nifi.datageneration.validation;

import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnDisabled;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.exception.ProcessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tags({"template", "output", "validator", "multi"})
@CapabilityDescription("A validator that allows you to chain multiple template output validators in sequential order. Each new validator " +
        "is added as a dynamic property with the following naming convention: <step number>.name. Ex: 1.firstStep, 2.secondStep.")
public class MultiStageValidator extends DynamicValidator implements TemplateOutputValidator {
    private volatile List<TemplateOutputValidator> validators;
    public static final Pattern DYNAMIC_NAME_VALIDATOR = Pattern.compile("^([\\d]+)\\.");

    @Override
    public PropertyDescriptor getSupportedDynamicPropertyDescriptor(String name) {
        Matcher matcher = DYNAMIC_NAME_VALIDATOR.matcher(name);
        if (matcher.lookingAt()) {
            return super.getSupportedDynamicPropertyDescriptor(name);
        } else {
            throw new ProcessException("Property name patterns should start with a number and a period. Ex. \"1.firstStep\"");
        }
    }

    @OnEnabled
    public void onEnabled(ConfigurationContext context) {
        Map<Integer, TemplateOutputValidator> _map = new HashMap<>();
        for (PropertyDescriptor desc : context.getProperties().keySet()) {
            if (desc.isDynamic()) {
                String[] parts = desc.getName().split("\\.");
                int key = Integer.valueOf(parts[0]);
                _map.put(key, context.getProperty(desc).asControllerService(TemplateOutputValidator.class));
            }
        }
        List<Integer> _keys = new ArrayList<>(_map.keySet());
        Collections.sort(_keys);
        List<TemplateOutputValidator> _temp = new ArrayList<>();
        for (Integer x : _keys) {
            _temp.add(_map.get(x));
        }

        validators = _temp;
    }

    @OnDisabled
    public void onDisabled() {
        validators = null;
    }

    @Override
    public void validate(String input, Map<String, String> attributes) {
        for (TemplateOutputValidator validator : validators) {
            validator.validate(input, attributes);
        }
    }
}
