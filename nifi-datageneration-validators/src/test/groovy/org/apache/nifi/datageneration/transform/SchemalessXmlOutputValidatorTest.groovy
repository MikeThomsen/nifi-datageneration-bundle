package org.apache.nifi.datageneration.transform

import org.apache.nifi.datageneration.validation.SchemalessXmlOutputValidator
import org.apache.nifi.processor.exception.ProcessException
import org.apache.nifi.util.TestRunners
import org.junit.Assert
import org.junit.Test

class SchemalessXmlOutputValidatorTest {
    @Test
    void test() {
        def runner = TestRunners.newTestRunner(ValidationTestProcessor.class)
        def validator = new SchemalessXmlOutputValidator()
        runner.addControllerService("validator", validator)
        runner.setProperty(ValidationTestProcessor.VALIDATOR, "validator")
        runner.enableControllerService(validator)

        def xml = """
            <document>
        """

        def exception
        try {
            validator.validate(xml, [:])
        } catch (Exception ex) {
            exception = ex
        } finally {
            Assert.assertNotNull(exception)
            Assert.assertTrue(exception instanceof ProcessException)
        }

        validator.validate("<document/>", [:])
    }
}
