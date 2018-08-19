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
            "1.first": new Validator1(),
            "2.second": new Validator2(),
            "3.third": new Validator3()
        ]

        validators.each {
            runner.addControllerService(it.value.class.name, it.value)
            runner.setProperty(validator, it.key, it.value.class.name)
            runner.enableControllerService(it.value)
        }
        runner.enableControllerService(validator)
        runner.assertValid()
    }

    @Test
    void testBadProperties() {
        def validators = [
            "1.first": new Validator1(),
            "2.second": new Validator2(),
            "third.3": new Validator3()
        ]

        Throwable exception
        try {
            validators.each {
                runner.addControllerService(it.value.class.name, it.value)
                runner.setProperty(validator, it.key, it.value.class.name)
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
}

class Validator1 extends AbstractControllerService implements TemplateOutputValidator {
    Map results

    @Override
    void validate(String input, Map<String, String> attributes) {
        results[this.class.name] = true
    }
}

class Validator2 extends AbstractControllerService implements TemplateOutputValidator {
    Map results

    @Override
    void validate(String input, Map<String, String> attributes) {
        results[this.class.name] = true
    }
}

class Validator3 extends AbstractControllerService implements TemplateOutputValidator {
    Map results

    @Override
    void validate(String input, Map<String, String> attributes) {
        results[this.class.name] = true
    }
}