package org.apache.nifi.datageneration.templates;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CurrentTimeFunction implements Function {
    @Override
    public Object execute(Map<String, Object> map, PebbleTemplate pebbleTemplate, EvaluationContext evaluationContext, int i) {
        return System.currentTimeMillis();
    }

    @Override
    public List<String> getArgumentNames() {
        return Collections.emptyList();
    }
}
