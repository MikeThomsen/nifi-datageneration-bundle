package org.apache.nifi.datageneration.templates

import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Before
import org.junit.Test

class TemplateProcessorTest {
    TemplateRegistry registry
    TestRunner runner

    @Before
    void before() {
        registry = new MockRegistry()
        runner = TestRunners.newTestRunner(TemplateProcessor.class)
        runner.addControllerService("registry", registry)
        runner.enableControllerService(registry)
        runner.setProperty(TemplateProcessor.REGISTRY, "registry")
        runner.assertValid()
    }

    @Test
    void testNormalPassthrough() {
        runner.enqueue("{}", ["template.name": "test"])
        runner.run()
        runner.assertTransferCount(TemplateProcessor.REL_FAILURE, 0)
        runner.assertTransferCount(TemplateProcessor.REL_ORIGINAL, 1)
        runner.assertTransferCount(TemplateProcessor.REL_SUCCESS, 1)

        runner.clearTransferState()
        runner.enqueue("{}", ["template.text": "{{test}}"])
        runner.run()
        runner.assertTransferCount(TemplateProcessor.REL_FAILURE, 0)
        runner.assertTransferCount(TemplateProcessor.REL_ORIGINAL, 1)
        runner.assertTransferCount(TemplateProcessor.REL_SUCCESS, 1)
    }

    @Test
    void testNoTemplateAttributes() {
        runner.enqueue("{}")
        runner.run()
        runner.assertTransferCount(TemplateProcessor.REL_FAILURE, 1)
        runner.assertTransferCount(TemplateProcessor.REL_ORIGINAL, 0)
        runner.assertTransferCount(TemplateProcessor.REL_SUCCESS, 0)
    }

    @Test
    void testInternalFailureCleanlyHandled() {
        ((MockRegistry)registry).doFailure = true
        runner.enqueue("{}")
        runner.run()
        runner.assertTransferCount(TemplateProcessor.REL_FAILURE, 1)
        runner.assertTransferCount(TemplateProcessor.REL_ORIGINAL, 0)
        runner.assertTransferCount(TemplateProcessor.REL_SUCCESS, 0)
    }
}