package org.apache.nifi.datageneration.transform

import org.apache.nifi.controller.AbstractControllerService
import org.apache.nifi.datageneration.validation.MultiStageValidator
import org.apache.nifi.datageneration.validation.TemplateOutputValidator
import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MultiStageValidatorTest {
    TestRunner runner
    MultiStageValidator validator

    @Before
    void before() {
        runner = TestRunners.newTestRunner(ValidationTestProcessor.class)
        validator = new MultiStageValidator()
        runner.addControllerService("validator", validator)
        runner.setProperty(ValidationTestProcessor.VALIDATOR, "validator")
    }

    @Test
    void testGoodProperties() {
        def validators = [
            "1.first": new IncrementalValidator(instance: 1),
            "2.second": new IncrementalValidator(instance: 2),
            "3.third": new IncrementalValidator(instance: 3)
        ]

        validators.each {
            runner.addControllerService(it.key, it.value)
            runner.setProperty(validator, it.key, it.key)
            runner.enableControllerService(it.value)
        }
        runner.enableControllerService(validator)
        runner.assertValid()
    }

    @Test
    void testBadProperties() {
        def validators = [
            "1.first": new IncrementalValidator(),
            "2.second": new IncrementalValidator(),
            "third.3": new IncrementalValidator()
        ]

        Throwable exception
        try {
            validators.each {
                runner.addControllerService(it.key, it.value)
                runner.setProperty(validator, it.key, it.key)
                runner.enableControllerService(it.value)
            }
            runner.enableControllerService(validator)
        } catch (Throwable t) {
            exception = t
        } finally {
            Assert.assertNotNull(exception)
            Assert.assertTrue(exception.message.contains("Property name patterns should start with a number and a period"))
        }
        runner.assertNotValid()
    }
    
    @Test
    void testExecution() {
        testGoodProperties()
        validator.validate("Test message", [:])

        Assert.assertEquals(3, IncrementalValidator.NAMES.size())
        def x = IncrementalValidator.NAMES
        int previous = 0
        IncrementalValidator.NAMES.each { name ->
            Assert.assertTrue("Out of order",name > previous)
            previous = name
        }
    }
}

class IncrementalValidator extends AbstractControllerService implements TemplateOutputValidator {
    static final List<Integer> NAMES = []
    int instance

    @Override
    void validate(String input, Map<String, String> attributes) {
        NAMES << instance
    }
}
