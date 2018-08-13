@Grab(group='com.github.javafaker', module='javafaker', version='0.15')
import com.github.javafaker.Faker
import static groovy.json.JsonOutput.*

def exclude = [
  "equals",
  "getClass",
  "hashCode",
  "notify",
  "notifyAll",
  "toString",
  "wait",
  "wait",
  "wait"
]

def titleCase = {
	def first = it[0].toUpperCase()
	"${first}${it[1..it.length()-1]}"
}

def skeleton = """
package org.apache.nifi.datageneration.templates.faker.functions;

import com.github.javafaker.Faker;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.util.Map;

public class %s extends AbstractFakerFunction {
    public %s(Faker faker) {
        super(faker);
    }

    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    	return faker.%s().%s();
    }
} 
"""

def faker = new Faker()
def items = [:]
faker.metaClass.methods.each { 
	if (it.returnType.name.startsWith("com.github.javafaker") && it.name != "instance" && it.name != "options") {
		def parent = titleCase it.name
		println parent + "\t" + it.returnType
		faker."${it.name}"().metaClass.methods.findAll { x -> !exclude.contains(x.name) }.each { inner ->
			println "\t* " + titleCase(inner.name) + "\t\t"
			def item = inner.name.replaceAll(/([A-Z])/, /\_$1/).toLowerCase()
			def full = "${it.name.toLowerCase()}_${item}"
			items[full] = [
				function: "${parent}${titleCase(inner.name)}",
				outer: it.name,
				inner: inner.name
			]
		}
	}
}

def keys = items.keySet().sort()
def sb = new StringBuilder()
keys.each { key ->
	sb.append("\tput(\"${key}\", new ${items[key]['function']}Function(faker)),\n")
}

new File("declarations.txt").write(sb.toString())

def unitTestSkeleton = """
	@Test
	void test%s() {
		def result = basicTemplateTest("/templates/%s.template")
		postExecute(result) { }
	}
"""

def test = new StringBuilder()
test.append("""
package org.apache.nifi.datageneration.templates.faker.functions

import org.junit.Test

class FunctionTest {
""")

keys.each { key ->
	def item = items[key]
	def name = "${item['function']}Function"
	def file = new File("src/main/java/org/apache/nifi/datageneration/templates/faker/functions/${name}.java")
	if (!file.exists()) {
		file.write(
				String.format(skeleton, name, name, item['outer'], item['inner'])
		)
	}

	if (!file.exists()) {
		file = new File("src/test/resources/templates/${key}_test.template")
		file.write("{{ ${key}() }}")
	}

	test.append(String.format(unitTestSkeleton, name, key))
}
test.append("}")
def file = new File("src/test/groovy/org/apache/nifi/datageneration/templates/faker/functions/FunctionTest.groovy")
if (!file.exists()) {
	file.write(test.toString())
}

println("Done")