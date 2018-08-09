package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;

import java.util.Map;
import java.util.Scanner;

public class RawTextHandler implements PdfHandler {
    @Override
    public void handle(Document document, byte[] input, Map<String, String> attributes) throws Exception {
        String content = new String(input);
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
