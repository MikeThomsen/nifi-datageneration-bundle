package org.apache.nifi.datageneration.transform

import com.itextpdf.kernel.pdf.PdfReader
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

    def dump = { name, result ->
        def writer = new FileOutputStream("/tmp/${name}.pdf")
        writer.write(result)
        writer.close()
    }

    @Test
    void testTransformRawText() {
        def text = """
            Testing
            
            Testing
            
            Testing
        """.bytes

        def result = transformer.transform(text, [:])

        def reader = new PdfReader(new ByteArrayInputStream(result))
        Assert.assertTrue(reader.fileLength > 0)
    }

    @Test
    void testTransformHtml() {
        def html = """
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

        runner.disableControllerService(transformer)
        runner.setProperty(transformer, PdfTransformer.HANDLER, PdfTransformer.HTML)
        runner.enableControllerService(transformer)

        def result = transformer.transform(html)

        def reader = new PdfReader(new ByteArrayInputStream(result))

        Assert.assertTrue(reader.fileLength > 0)

        dump "html", result
    }

}
