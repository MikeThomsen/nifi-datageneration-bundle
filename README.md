## NiFi Data Generation Bundle

It provides a few components:

1. TemplateProcessor - a generic processor that uses a configured **TemplateRegistry** controller service to build data from incoming flowfiles.
2. PebbleTemplateRegistry - a controller service that uses the Pebble template language to generate data from configured templates.

A template can be configured in one of two ways:

1. As a dynamic property on the *TemplateRegistry* and then referenced with the flowfile attribute *template.name*
2. As an attribute on the flowfile containing the full template assigned to the attribute key *template.text*

### Pebble Template Registry

The Pebble Template Registry supports Pebble extensions via its Extension JARs and Extension Classes configuration properties. 
If configured properly, these will automatically load all supplied implementations of the Pebble `Extension` interface and 
configure the PebbleEngine to use them.