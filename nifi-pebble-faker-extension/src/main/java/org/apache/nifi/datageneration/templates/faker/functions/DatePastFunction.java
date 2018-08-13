
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DatePastFunction extends AbstractOffsetDateFunction {
    public DatePastFunction(Faker faker) {
        super(faker);
    }

    @Override
    Date getDate(int atMost, TimeUnit unit, Date startFrom) {
        return startFrom != null
            ? faker.date().past(atMost, unit, startFrom)
            : faker.date().past(atMost, unit);
    }
} 
