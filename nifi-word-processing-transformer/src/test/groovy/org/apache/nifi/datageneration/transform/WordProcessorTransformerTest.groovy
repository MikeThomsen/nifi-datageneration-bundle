package org.apache.nifi.datageneration.transform

import org.apache.nifi.util.TestRunners
import org.junit.Test

class WordProcessorTransformerTest {
    @Test
    void test() {
        def runner = TestRunners.newTestRunner(WPTestProcessor.class)
        def transformer = new WordProcessorTransformer()
        runner.addControllerService("transformer", transformer)
        runner.setProperty(WPTestProcessor.TRANSFORMER, "transformer")
        runner.enableControllerService(transformer)
        runner.assertValid()
    }
}
