package org.apache.nifi.datageneration.templates;

public class TemplateException extends RuntimeException {
    public TemplateException(String msg) {
        super(msg);
    }

    public TemplateException(Throwable t) {
        super(t);
    }
}
