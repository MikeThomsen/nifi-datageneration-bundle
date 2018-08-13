package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.extension.Function;

import java.util.Collections;
import java.util.List;

public abstract class AbstractFakerFunction implements Function {
    protected Faker faker;

    public AbstractFakerFunction(Faker faker) {
        this.faker = faker;
    }

    @Override
    public List<String> getArgumentNames() {
        return Collections.emptyList();
    }
}
