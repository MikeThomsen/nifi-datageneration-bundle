package org.apache.nifi.datageneration.templates;

public interface TemplateRegistry {
    /**
     * Add a template to the registry.
     * @param name The name of the template to use in lookups.
     * @param text The raw content of the template.
     */
    void addTemplate(String name, String text);

    /**
     * Generate a template by looking it up by name in the registry and building it against the model supplied by the
     * byte array from a flowfile.
     * @param model The data model for the template.
     * @param name  The name of the template.
     * @return
     */
    String generateByName(byte[] model, String name);

    /**
     * Generate a template by directly supplying the text.
     * @param model The data model for the template.
     * @param text  The raw text of the template.
     * @return
     */
    String generateByText(byte[] model, String text);

    /**
     * Unregister a template with the template library.
     * @param name The name of the template to remove.
     */
    void removeTemplate(String name);
}
