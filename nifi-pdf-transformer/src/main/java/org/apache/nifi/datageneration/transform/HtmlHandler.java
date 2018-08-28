package org.apache.nifi.datageneration.transform;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class HtmlHandler implements PdfHandler {
    @Override
    public byte[] handle(byte[] input, Map<String, String> attributes) throws Exception {
        String content = new String(input);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(content, out);
        out.close();

        return out.toByteArray();
    }
}
