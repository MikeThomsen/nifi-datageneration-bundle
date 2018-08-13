
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

public class DateBetweenFunction extends AbstractFakerFunction {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String FORMAT = "format";
    public static final String START = "start";
    public static final String END   = "end";

    public DateBetweenFunction(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (!args.containsKey(START)) {
            throw new IllegalArgumentException(String.format("Missing argument %s", START));
        } else if (!args.containsKey(END)) {
            throw new IllegalArgumentException(String.format("Missing argument %s", END));
        }

        String formatStr = args.containsKey(FORMAT) ? (String) args.get(FORMAT) : DEFAULT_FORMAT;
        String startStr  = (String) args.get(START);
        String endStr    = (String) args.get(END);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date start, end;

        try {
            start = dateFormat.parse(startStr);
            end   = dateFormat.parse(endStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return faker.date().between(start, end);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList(FORMAT, START, END);
    }
} 
