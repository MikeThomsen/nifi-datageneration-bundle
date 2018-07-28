package org.apache.nifi.datageneration.templates

import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Assert
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

    @Test
    void testInclude() {
        registry.addTemplate("module", getClass().getResourceAsStream("/include_module.txt").text)
        registry.addTemplate("include_test", getClass().getResourceAsStream("/include_main.txt").text)
        def data = getClass().getResourceAsStream("/include_model.json").text
        def output = registry.generateByName(data?.bytes, "include_test")

        Assert.assertTrue(output?.trim().length() > 0)
        def parsed = new groovy.json.JsonSlurper().parseText(data)
        parsed.each { kv ->
            kv.value.each { inner ->
                Assert.assertTrue(output.contains(inner.value))
            }
        }
    }
}
