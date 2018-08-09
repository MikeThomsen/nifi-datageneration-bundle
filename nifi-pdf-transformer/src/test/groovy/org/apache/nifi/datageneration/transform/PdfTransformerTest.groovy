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

    static final byte[] BASIC_HTML = """
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
    void testTransformHtmWithCssSelector() {
        cssTest("p", "test")
    }

    @Test
    void testTransformHtmWithBasicBodyWalk() {
        cssTest("", "css-no-selector")
    }

    @Test
    void testTransformHtmlWithBasicDOMWalk() {
        cssTest("", "asterick", """
            <p>Hello, world</p>
           Hello, world
           Buongiorno, mondo!<br/>
           <p>Ciao</p>
        """.bytes)
    }

    void cssTest(String selector, String fileName) {
        cssTest(selector, fileName, BASIC_HTML)
    }

    void cssTest(String selector, String fileName, byte[] html) {
        runner.disableControllerService(transformer)
        runner.setProperty(transformer, PdfTransformer.HANDLER, PdfTransformer.HTML)
        runner.enableControllerService(transformer)



        def result = transformer.transform(html, [(HtmlHandler.CSS_SELECTOR_ATTRIBUTE): selector])
        def reader = new PdfReader(result)
        Assert.assertTrue(reader.numberOfPages == 1)

        def file = new File("/tmp/${fileName}.pdf")
        def writer = new FileOutputStream(file)
        writer.write(result)
        writer.close()
    }
}
