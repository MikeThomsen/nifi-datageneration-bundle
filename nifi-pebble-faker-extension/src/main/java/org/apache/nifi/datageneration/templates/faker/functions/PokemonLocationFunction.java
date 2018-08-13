
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Map;

public class PokemonLocationFunction extends AbstractFakerFunction {
    public PokemonLocationFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    	return faker.pokemon().location();
    }
} 
