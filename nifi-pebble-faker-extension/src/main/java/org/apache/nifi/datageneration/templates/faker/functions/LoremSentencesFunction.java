
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LoremSentencesFunction extends AbstractFakerFunction {
    public static final String LIMIT = "limit";

    public LoremSentencesFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        int limit = args.containsKey(LIMIT) ? Integer.valueOf((String) args.getOrDefault(LIMIT, "5")) : 5;
    	return faker.lorem().sentences(limit);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(LIMIT);
    }
} 
