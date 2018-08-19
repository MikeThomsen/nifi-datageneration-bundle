package org.apache.nifi.datageneration.transform

import org.apache.nifi.controller.AbstractControllerService
import org.apache.nifi.datageneration.validation.TemplateOutputValidator
import org.apache.nifi.datageneration.validation.ValidationRouter
import org.apache.nifi.util.TestRunners
import org.junit.Assert
import org.junit.Test

class ValidationRouterTest {
    @Test
    void test() {
        def runner = TestRunners.newTestRunner(ValidationTestProcessor.class)
        def validator = new ValidationRouter()
        def results = [:]
        def helpers = [
            new ValidatorA(results: results), new ValidatorB(results: results), new ValidatorC(results: results)
        ]
        runner.addControllerService("validator", validator)
        helpers.each { helper ->
            runner.addControllerService(helper.class.name, helper)
            runner.setProperty(validator, helper.class.name, helper.class.name)
            runner.enableControllerService(helper)
        }
        runner.enableControllerService(validator)

        helpers.each { helper ->
            def attributes = [
                (ValidationRouter.DEFAULT_ATTRIBUTE): helper.class.name
            ]
            validator.validate("Test message", attributes)
        }

        Assert.assertTrue(results.size() == 3)
        helpers.each { helper ->
            Assert.assertTrue(results.containsKey(helper.class.name))
            Assert.assertTrue(results[helper.class.name])
        }
    }
}

class ValidatorA extends AbstractControllerService implements TemplateOutputValidator {
    Map results

    @Override
    void validate(String input, Map<String, String> attributes) {
        results[this.class.name] = true
    }
}

class ValidatorB extends AbstractControllerService implements TemplateOutputValidator {
    Map results

    @Override
    void validate(String input, Map<String, String> attributes) {
        results[this.class.name] = true
    }
}

class ValidatorC extends AbstractControllerService implements TemplateOutputValidator {
    Map results

    @Override
    void validate(String input, Map<String, String> attributes) {
        results[this.class.name] = true
    }
}