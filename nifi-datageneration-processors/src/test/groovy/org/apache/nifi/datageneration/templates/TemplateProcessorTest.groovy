package org.apache.nifi.datageneration.templates

import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Assert
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

    @Test
    void testReadNewMimeType() {
        runner.enqueue("{}", ["template.text": "{{message}}"])
        runner.run()

        runner.assertTransferCount(TemplateProcessor.REL_SUCCESS, 1)
        def flowFile = runner.getFlowFilesForRelationship(TemplateProcessor.REL_SUCCESS)[0]
        Assert.assertEquals(TemplateProcessor.DEFAULT_OUTPUT_MIME_TYPE, flowFile.getAttribute(TemplateProcessor.OUTPUT_MIME_TYPE))

        runner.clearTransferState()

        runner.setProperty(TemplateProcessor.MIME_TYPE, '${out.mime.type}')
        runner.enqueue("{}", ["template.text": "{{message}}", "out.mime.type": "text/html"])
        runner.run()
        runner.assertTransferCount(TemplateProcessor.REL_FAILURE, 0)
        runner.assertTransferCount(TemplateProcessor.REL_ORIGINAL, 1)
        runner.assertTransferCount(TemplateProcessor.REL_SUCCESS, 1)
        flowFile = runner.getFlowFilesForRelationship(TemplateProcessor.REL_SUCCESS)[0]
        Assert.assertEquals("text/html", flowFile.getAttribute(TemplateProcessor.OUTPUT_MIME_TYPE))
    }
}