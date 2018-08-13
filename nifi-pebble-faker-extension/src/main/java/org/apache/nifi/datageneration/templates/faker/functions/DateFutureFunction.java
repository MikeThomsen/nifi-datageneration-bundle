
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DateFutureFunction extends AbstractOffsetDateFunction {
    public static final String AT_MOST = "at_most";
    public static final String TIME_UNIT = "time_unit";
    public static final String START_FROM = "start_from";
    public static final String DATE_FORMAT = "date_format";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public DateFutureFunction(Faker faker) {
        super(faker);
    }

    @Override
    Date getDate(int atMost, TimeUnit unit, Date startFrom) {
        return startFrom != null
            ? faker.date().future(atMost, unit, startFrom)
            : faker.date().future(atMost, unit);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(AT_MOST, TIME_UNIT, START_FROM, DATE_FORMAT);
    }
} 
