
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RandomNextIntFunction extends AbstractFakerFunction {
    public static final String LIMIT = "limit";

    public RandomNextIntFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(LIMIT)) {
            throw new IllegalArgumentException("Missing parameter \"limit.\"");
        }

        int limit = ((Long)args.get(LIMIT)).intValue();

    	return faker.random().nextInt(limit);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(LIMIT);
    }
} 
