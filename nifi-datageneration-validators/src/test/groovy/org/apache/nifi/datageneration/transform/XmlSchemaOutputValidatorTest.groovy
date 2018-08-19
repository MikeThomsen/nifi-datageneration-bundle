package org.apache.nifi.datageneration.transform

import org.apache.nifi.datageneration.validation.TemplateOutputValidator
import org.apache.nifi.datageneration.validation.XmlSchemaOutputValidator
import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class XmlSchemaOutputValidatorTest {
    TestRunner runner
    TemplateOutputValidator validator

    @Before
    void before() {
        runner = TestRunners.newTestRunner(ValidationTestProcessor.class)
        validator = new XmlSchemaOutputValidator()
        runner.addControllerService("validator", validator)
        runner.setProperty(ValidationTestProcessor.VALIDATOR, "validator")
    }

    @Test
    void testSchemaLocation() {
        runner.setProperty(validator, XmlSchemaOutputValidator.SCHEMA_LOCATION, "src/test/resources/employee.xsd")
        runner.enableControllerService(validator)
        runner.assertValid()

        validator.validate(new File("src/test/resources/employee.xml").text, [:])
    }

    @Test
    void testSchemaBody() {
        runner.setProperty(validator, XmlSchemaOutputValidator.SCHEMA_BODY, new File("src/test/resources/employee.xsd").text)
        runner.enableControllerService(validator)
        runner.assertValid()

        validator.validate(new File("src/test/resources/employee.xml").text, [:])
    }

    @Test
    void testValidationRules() {
        Throwable error
        try {
            runner.enableControllerService(validator)
        } catch (Throwable t) {
            error = t
        } finally {
            Assert.assertNotNull(error)
            Assert.assertTrue(error.message.contains("because Schema Location or Schema Body must be set"))
        }
        error = null
        runner.assertNotValid()
        def file = new File("target/fake.xsd")
        file.write("<document/>")
        runner.setProperty(validator, XmlSchemaOutputValidator.SCHEMA_LOCATION, file.absolutePath)
        runner.setProperty(validator, XmlSchemaOutputValidator.SCHEMA_BODY, "<document/>")
        try {
            runner.enableControllerService(validator)
        } catch (Throwable t) {
            error = t
        } finally {
            Assert.assertNotNull(error)
            Assert.assertTrue(error.message.contains("Schema Location and Schema Body cannot both be set"))
        }
        runner.assertNotValid()
    }
}
