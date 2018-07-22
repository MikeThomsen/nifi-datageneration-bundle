package org.apache.nifi.datageneration.templates

import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Before
import org.junit.Test

class TestPebbleTemplateRegistry {
    PebbleTemplateRegistry registry
    TestRunner runner

    @Before
    void setup() throws Exception {
        runner = TestRunners.newTestRunner(PebbleTestProcessor.class)
        registry = new PebbleTemplateRegistry()
        runner.addControllerService("registry", registry)
        runner.enableControllerService(registry)
        runner.setProperty(PebbleTestProcessor.REGISTRY, "registry")
    }

    @Test
    void test() {
        runner.assertValid()
    }
}
