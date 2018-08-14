package org.apache.nifi.datageneration.randomlookup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.nifi.annotation.lifecycle.OnDisabled;
import org.apache.nifi.annotation.lifecycle.OnEnabled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.AbstractControllerService;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.datageneration.RandomLookupService;
import org.apache.nifi.expression.ExpressionLanguageScope;
import org.apache.nifi.lookup.LookupFailureException;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomJsonMapLookupService extends AbstractControllerService implements RandomLookupService<Map<String, Object>> {
    static final String NON_MAP_ID_KEY = "selectedValue";

    static final PropertyDescriptor FILE_PATH = new PropertyDescriptor.Builder()
            .displayName("File Path")
            .name("random-lookup-file-path")
            .description("Input file that acts as a data source. Caution: the entire file will be loaded into memory.")
            .addValidator(StandardValidators.FILE_EXISTS_VALIDATOR)
            .required(true)
            .expressionLanguageSupported(ExpressionLanguageScope.VARIABLE_REGISTRY)
            .build();

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        List<PropertyDescriptor> _temp = new ArrayList<>();
        _temp.add(FILE_PATH);
        return Collections.unmodifiableList(_temp);
    }

    private volatile Map<String, Object> dataSetHash;
    private volatile List<String> flatKeyList;
    private volatile List<Map<String, Object>> dataSetList;
    private volatile boolean isList;
    private Random random;

    @OnEnabled
    public void onEnabled(final ConfigurationContext context) throws IOException {
        final String path = context.getProperty(FILE_PATH).evaluateAttributeExpressions().getValue();
        ObjectMapper mapper = new ObjectMapper();
        String firstLine = peekAtFile(path);
        if (firstLine.startsWith("[")) {
            dataSetList = mapper.readValue(new File(path), List.class);
            isList = true;
        } else {
            dataSetHash = mapper.readValue(new File(path), Map.class);
            flatKeyList = dataSetHash.keySet().stream()
                    .collect(Collectors.toList());
            isList = false;
        }
        random = new Random();
    }

    @OnDisabled
    public void onDisabled() {
        dataSetHash = null;
        dataSetList = null;
        flatKeyList = null;
    }

    private String peekAtFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        reader.close();
        return line;
    }

    private int getRandomIndex() {
        int ceiling = isList ? dataSetList.size() : flatKeyList.size();
        int pick = random.nextInt(ceiling);

        return pick;
    }

    private Map<String, Object> pickElement(int index) throws LookupFailureException {
        Map<String, Object> obj;
        if (isList) {
            Object o = dataSetList.get(index);
            if (o instanceof Map) {
                obj = (Map)o;
            } else {
                obj = new HashMap<>();
                obj.put(NON_MAP_ID_KEY, o);
            }
        } else {
            String key = flatKeyList.get(index);
            Object o = dataSetHash.get(key);

            obj = new HashMap<>();
            obj.put(key, o);
        }

        return obj;
    }

    @Override
    public Optional<Map<String, Object>> lookup(Map coordinates) throws LookupFailureException {
        int index = getRandomIndex();
        return Optional.ofNullable(pickElement(index));
    }

    @Override
    public Class<?> getValueType() {
        return Map.class;
    }

    @Override
    public Set<String> getRequiredKeys() {
        return null;
    }
}
