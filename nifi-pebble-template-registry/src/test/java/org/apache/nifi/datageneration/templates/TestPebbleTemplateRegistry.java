package org.apache.nifi.datageneration.templates;

import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;

public class TestPebbleTemplateRegistry {
    private PebbleTemplateRegistry registry;
    private TestRunner runner;

    @Before
    public void setup() throws Exception {
        runner = TestRunners.newTestRunner(PebbleTestProcessor.class);
        registry = new PebbleTemplateRegistry();
        runner.addControllerService("registry", registry);
        runner.enableControllerService(registry);
        runner.setProperty(PebbleTestProcessor.REGISTRY, "registry");
    }

    @Test
    public void test() {
        runner.assertValid();
    }
}
