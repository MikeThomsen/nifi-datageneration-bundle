## NiFi Data Generation Bundle

It provides a few components:

1. TemplateProcessor - a generic processor that uses a configured **TemplateRegistry** controller service to build data from incoming flowfiles.
2. PebbleTemplateRegistry - a controller service that uses the Pebble template language to generate data from configured templates.
3. PDF Transformer - a controller service that transforms the template's output into a PDF.
4. Word Processing Transformer - a controller service that transforms the template's output into a DOCX file.
5. Various validators for the raw output.

A template can be configured in one of two ways:

1. As a dynamic property on the *TemplateRegistry* and then referenced with the flowfile attribute *template.name*
2. As an attribute on the flowfile containing the full template assigned to the attribute key *template.text*

### Pebble Template Registry

The Pebble Template Registry supports Pebble extensions via its Extension JARs and Extension Classes configuration properties. 
If configured properly, these will automatically load all supplied implementations of the Pebble `Extension` interface and 
configure the PebbleEngine to use them.

### PDF and Word Processing Transformers

Important note: the template's output must be HTML that is written as valid XML. Doesn't have to be limited to XHTML, it just has to be 
syntactically valid XML (ex. all tags must be closed; `<br/>` vs `<br>`). To help identify problems from that issue, `SchemalessXmlOutputValidator` should be added
to the processor.