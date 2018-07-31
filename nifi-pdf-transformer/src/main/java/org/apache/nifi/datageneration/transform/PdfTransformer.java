package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.nifi.controller.AbstractControllerService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfTransformer extends AbstractControllerService implements TransformationService {
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

            doc.close();
            out.close();

            retVal = out.toByteArray();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
