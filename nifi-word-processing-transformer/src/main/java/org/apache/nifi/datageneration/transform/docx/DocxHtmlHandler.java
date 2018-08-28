package org.apache.nifi.datageneration.transform.docx;

import org.apache.nifi.datageneration.transform.WordProcessorHandler;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DocxHtmlHandler implements WordProcessorHandler {
    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) throws Exception {
        String content = new String(input);

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        XHTMLImporterImpl importer = new XHTMLImporterImpl(wordMLPackage);
        importer.setHyperlinkStyle("Hyperlink");
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                importer.convert(content, "/") );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wordMLPackage.save(out);
        out.close();

        return out.toByteArray();
    }

    @Override
    public String getMimeType() {
        return null;
    }
}
