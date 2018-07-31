package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;

import java.util.Map;
import java.util.Scanner;

public class RawTextHandler implements PdfHandler {
    @Override
    public void handle(Document document, byte[] model, Map<String, String> attributes) throws Exception {
        String content = new String(model);
        Scanner scanner = new Scanner(content);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String clean = line.trim();
            if (clean.length() > 0) {
                document.add(new Paragraph(clean));
            }
        }
    }
}
