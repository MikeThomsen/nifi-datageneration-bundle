package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.nifi.controller.AbstractControllerService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfTransformer extends AbstractControllerService implements TransformationService {
    public static final String MIME_TYPE = "application/pdf";

    @Override
    public byte[] transform(byte[] input) {
        return transform(input, null);
    }

    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] retVal = null;

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();

            String raw = new String(input);
            String[] lines = raw.split("\n\r");
            for (int x = 0; x < lines.length; x++) {
                String clean = lines[x].trim();
                if (!clean.isEmpty()) {
                    doc.add(new Paragraph(clean));
                }
            }

            doc.close();
            out.close();

            retVal = out.toByteArray();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }
}
