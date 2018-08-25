package org.apache.nifi.datageneration.transform;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Scanner;

public class RawTextHandler implements PdfHandler {
    @Override
    public byte[] handle(byte[] input, Map<String, String> attributes) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument document = new PdfDocument(writer);
        Document doc = new Document(document);
        String content = new String(input);
        Scanner scanner = new Scanner(content);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String clean = line.trim();
            if (clean.length() > 0) {
                doc.add(new Paragraph(clean));
            }
        }

        writer.close();
        out.close();

        return out.toByteArray();
    }
}
