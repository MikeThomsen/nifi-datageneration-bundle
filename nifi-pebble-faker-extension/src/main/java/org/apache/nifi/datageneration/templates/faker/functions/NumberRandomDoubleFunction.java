
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NumberRandomDoubleFunction extends AbstractFakerFunction {
    public static final String DECIMALS = "decimals";
    public static final String MIN = "min";
    public static final String MAX = "max";

    public NumberRandomDoubleFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(DECIMALS)) {
            throw new IllegalArgumentException("Missing parameter \"decimals.\"");
        }
        if (!args.containsKey(MIN)) {
            throw new IllegalArgumentException("Missing parameter \"min.\"");
        }
        if (!args.containsKey(MAX)) {
            throw new IllegalArgumentException("Missing parameter \"max.\"");
        }

        int decimals  = ((Long) args.get(DECIMALS)).intValue();
        long min      = (long) args.get(MIN);
        long max      = (long) args.get(MAX);
    	return faker.number().randomDouble(decimals, min, max);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(DECIMALS, MIN, MAX);
    }
} 
