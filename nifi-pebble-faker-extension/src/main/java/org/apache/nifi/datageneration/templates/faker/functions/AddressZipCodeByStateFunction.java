
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AddressZipCodeByStateFunction extends AbstractFakerFunction {
    public static final String STATE = "state";

    public AddressZipCodeByStateFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(STATE)) {
            throw new IllegalArgumentException("No \"state\" parameter was provided.");
        }
    	return faker.address().zipCodeByState((String) args.get(STATE));
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(STATE);
    }
} 
