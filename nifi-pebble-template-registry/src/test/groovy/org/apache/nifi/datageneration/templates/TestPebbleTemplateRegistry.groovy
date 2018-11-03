package org.apache.nifi.datageneration.templates

import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import static groovy.json.JsonOutput.*

class TestPebbleTemplateRegistry {
    PebbleTemplateRegistry registry
    TestRunner runner

    @Before
    void setup() throws Exception {
        runner = TestRunners.newTestRunner(PebbleTestProcessor.class)
        registry = new PebbleTemplateRegistry()
        runner.addControllerService("registry", registry)
        runner.setProperty(registry, PebbleTemplateRegistry.EXTENSION_JARS, "")
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

    @Test
    void testGenerateByName() {
        runner.disableControllerService(registry)
        runner.setProperty(registry, "my.template", "{{message}}")
        runner.enableControllerService(registry)
        def model = prettyPrint(toJson([
            message: "Hello, world"
        ]))
        def data = registry.generateByName(model.bytes, "my.template")
        Assert.assertTrue(data?.trim() == "Hello, world")
    }

    @Test
    void testGenerateByText() {
        def model = prettyPrint(toJson([
            message: "Hello, world"
        ]))
        def data = registry.generateByText(model.bytes, "{{message}}")
        Assert.assertTrue(data?.trim() == "Hello, world")
    }

    @Test
    void testErrorHandling() {
        def notJson = "||".bytes
        registry.addTemplate("test", "{{message}}")
        ["generateByName": ["test", "not here"], "generateByText": ["{{message}}", ""],].each { kv ->
            kv.value.each {
                def exception
                try {
                    registry."${kv.key}"(notJson, it)
                } catch (Exception ex) {
                    exception = ex
                } finally {
                    Assert.assertTrue(kv.key, exception instanceof TemplateException)
                }
            }
        }
    }

    @Test
    void testExtensions() {
        runner.disableControllerService(registry)
        runner.setProperty(registry, PebbleTemplateRegistry.EXTENSION_JARS, "target/test-lib/nifi-pebble-test-extension-1.8.0.jar")
        runner.setProperty(registry, PebbleTemplateRegistry.EXTENSION_CLASSES, "org.apache.nifi.datageneration.templates.TestExtension")
        runner.enableControllerService(registry)
        runner.assertValid()

        def exception = null
        def data = null
        try {
            data = registry.generateByText("{}".bytes, "{{current_time()}}")
        } catch (Exception ex) {
            exception = ex
        } finally {
            Assert.assertNull(exception)
            Assert.assertTrue(data?.trim().toLong() > 0)
        }
    }
}
