package org.apache.nifi.datageneration.transform

import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Before

class PdfTransformerTest {
    PdfTransformer transformer
    TestRunner runner

    @Before
    void setup() {
        transformer = new PdfTransformer()
        runner = TestRunners.newTestRunner(PdfTestProcessor.class)
        runner.addControllerService("transformer", transformer)
        runner.setProperty(PdfTestProcessor.TRANSFORMER, "transformer")
        runner.enableControllerService(transformer)
        runner.assertValid()
    }

    @Before
    void testTransformRawText() {

    }
}
