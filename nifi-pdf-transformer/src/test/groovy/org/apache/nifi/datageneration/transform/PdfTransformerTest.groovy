package org.apache.nifi.datageneration.transform

import com.lowagie.text.pdf.PdfReader
import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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

    @Test
    void testTransformRawText() {
        def text = """
            Testing
            
            Testing
            
            Testing
        """.bytes

        def result = transformer.transform(text, [:])

        def reader = new PdfReader(result)
        Assert.assertTrue(reader.numberOfPages == 1)
    }
}
