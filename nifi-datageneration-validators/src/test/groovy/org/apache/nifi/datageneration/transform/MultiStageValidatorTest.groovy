package org.apache.nifi.datageneration.transform

import org.apache.nifi.controller.AbstractControllerService
import org.apache.nifi.datageneration.validation.MultiStageValidator
import org.apache.nifi.datageneration.validation.TemplateOutputValidator
import org.apache.nifi.util.TestRunner
import org.apache.nifi.util.TestRunners
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