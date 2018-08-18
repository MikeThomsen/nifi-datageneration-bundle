package org.apache.nifi.datageneration.templates.faker.functions;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UUIDFunction implements Function {
    @Override
    public Object execute(Map<String, Object> map, PebbleTemplate pebbleTemplate, EvaluationContext evaluationContext, int i) {
        return UUID.randomUUID().toString();
    }

    @Override
    public List<String> getArgumentNames() {
        return Collections.emptyList();
    }
}
