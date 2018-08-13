
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NumberNumberBetweenFunction extends AbstractFakerFunction {
    public static final String MIN = "min";
    public static final String MAX = "max";

    public NumberNumberBetweenFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(MIN) && args.containsKey(MAX)) {
            throw new IllegalArgumentException("Parameters min and max must both be specified.");
        }

        int min = ((Long)args.get(MIN)).intValue();
        int max = ((Long)args.get(MAX)).intValue();

        if (min == max) {
            throw new IllegalArgumentException("Min and max cannot be the same value.");
        } else if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max.");
        }

    	return faker.number().numberBetween(min, max);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(MIN, MAX);
    }
} 
