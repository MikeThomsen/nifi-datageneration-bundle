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

    @Test
    void testTransformHtmlWithCssSelector() {
        runner.disableControllerService(transformer)
        runner.setProperty(transformer, PdfTransformer.HANDLER, PdfTransformer.HTML)
        runner.enableControllerService(transformer)

        def text = """
            <html>
            <body>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                <p>Donec semper massa vitae auctor efficitur.</p>
                <p>Nunc dapibus nisi sed <a href="http://www.duckduckgo.com">mauris</a> pellentesque aliquam.</p>
                <p>Ut eleifend purus id erat consequat, non scelerisque turpis bibendum.</p>
                <p><a href="http://github.com">Nullam interdum urna eget sagittis facilisis.</a></p>
                <p>Vestibulum ac quam sollicitudin, rhoncus eros quis, sagittis tellus.</p>
            </body>
            </html>
        """.bytes

        def result = transformer.transform(text, [(HtmlHandler.CSS_SELECTOR_ATTRIBUTE): "p"])
        def reader = new PdfReader(result)
        Assert.assertTrue(reader.numberOfPages == 1)

        def file = new File("/tmp/test.pdf")
        def writer = new FileOutputStream(file)
        writer.write(result)
        writer.close()
    }
}
