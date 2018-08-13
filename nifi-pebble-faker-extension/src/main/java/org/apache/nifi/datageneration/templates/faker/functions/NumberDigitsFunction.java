
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NumberDigitsFunction extends AbstractFakerFunction {
    public static final String COUNT = "count";

    public NumberDigitsFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(COUNT)) {
            throw new IllegalArgumentException("Missing parameter \"count\"");
        }

        int count = ((Long) args.get(COUNT)).intValue();

    	return faker.number().digits(count);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(COUNT);
    }
} 
