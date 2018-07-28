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
        runner.setProperty(registry, PebbleTemplateRegistry.EXTENSION, "")
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

//    @Test
//    void testExtensions() {
//        runner.disableControllerService(registry)
//        runner.setProperty(registry, PebbleTemplateRegistry.EXTENSION, "/tmp/test-extension.jar")
//        runner.enableControllerService(registry)
//        runner.assertValid()
//
//        registry.addTemplate("extension.test", "{{current_time()}}")
//
//        def data = registry.generateByName("{}".bytes, "extension.test")
//        Assert.assertTrue(data?.trim().length() > 0)
//        Assert.assertTrue(new Long(data) > 0)
//    }
}
