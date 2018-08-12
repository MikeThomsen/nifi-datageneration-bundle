package org.apache.nifi.datageneration.transform.docx;

import org.apache.nifi.datageneration.transform.WordProcessorHandler;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DocxRawTextHandler implements WordProcessorHandler {
    @Override
    public byte[] transform(byte[] input, Map<String, String> attributes) throws Exception {
        byte[] retVal = null;
        String content = new String(input);
        if (!content.isEmpty()) {
            String[] parts = content.split("[\\n\\r]");
            ObjectFactory factory = Context.getWmlObjectFactory();
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            P spc = factory.createP();
            R rspc = factory.createR();
            int added = 0;
            for (int x = 0; x < parts.length; x++) {
                String clean = parts[x].trim();
                if (!clean.isEmpty()) {
                    Text _temp = factory.createText();
                    _temp.setValue(clean);
                    rspc.getContent().add(_temp);
                    rspc.getContent().add(factory.createBr());
                    if (x + 1 < parts.length) {
                        Text br = factory.createText();
                        br.setValue("\r\n");
                        rspc.getContent().add(br);
                    }
                    added++;
                }
            }

            if (added > 0) {
                spc.getContent().add(rspc);
                mainDocumentPart.addObject(spc);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                wordPackage.save(out);
                out.close();

                retVal = out.toByteArray();
            }
        }


        return retVal;
    }
}
