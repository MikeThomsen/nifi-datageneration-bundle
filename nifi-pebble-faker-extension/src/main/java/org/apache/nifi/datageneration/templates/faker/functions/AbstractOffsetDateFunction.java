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

public abstract class AbstractOffsetDateFunction extends AbstractFakerFunction {
    public static final String AT_MOST = "at_most";
    public static final String TIME_UNIT = "time_unit";
    public static final String START_FROM = "start_from";
    public static final String DATE_FORMAT = "date_format";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public AbstractOffsetDateFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(AT_MOST)) {
            throw new IllegalArgumentException(String.format("Missing parameter %s", AT_MOST));
        } else if (!args.containsKey(TIME_UNIT)) {
            throw  new IllegalArgumentException(String.format("Missing parameter %s", TIME_UNIT));
        }

        int atMost          = ((Long)args.get(AT_MOST)).intValue();
        String timeUnitStr  = args.get(TIME_UNIT).toString();
        String format       = args.containsKey(DATE_FORMAT) ? (String) args.get(DATE_FORMAT) : DEFAULT_FORMAT;
        String startFromStr = args.containsKey(START_FROM) ? (String) args.get(START_FROM) : "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        TimeUnit unit = TimeUnit.valueOf(timeUnitStr.toUpperCase());

        Date startFrom;
        try {
            if (!startFromStr.isEmpty()) {
                startFrom = dateFormat.parse(startFromStr);
            } else {
                startFrom = null;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return getDate(atMost, unit, startFrom);
    }

    abstract Date getDate(int atMost, TimeUnit unit, Date startFrom);

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(AT_MOST, TIME_UNIT, START_FROM, DATE_FORMAT);
    }
}
