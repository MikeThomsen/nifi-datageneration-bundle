package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import org.apache.nifi.processor.exception.ProcessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HtmlHandler implements PdfHandler {
    public static String CSS_SELECTOR_ATTRIBUTE = "css.selector";
    public static final List<String> SHOULD_SKIP = Arrays.asList(
        "script", "link", "style"
    );

    @Override
    public void handle(Document document, byte[] input, Map<String, String> attributes) throws Exception {
        String content = new String(input);
        org.jsoup.nodes.Document soupDocument = Jsoup.parse(content);
        if (attributes.containsKey(CSS_SELECTOR_ATTRIBUTE)) {
            handleCssSelector(document, soupDocument, attributes.get(CSS_SELECTOR_ATTRIBUTE));
        }
    }

    private void handleCssSelector(Document document, org.jsoup.nodes.Document soupDocument, String selector) throws DocumentException {
        Elements selected = soupDocument.select(selector);
        if (selected.isEmpty()) {
            throw new ProcessException(String.format("CSS selector \"%s\" did not find anything.", selector));
        }

        for (int x = 0; x < selected.size(); x++) {
            Element element = selected.get(x);
            Paragraph paragraph = new Paragraph();
            convertElement(element, paragraph);
            boolean hasChildren = element.children().size() > 0;
            if (!paragraph.isEmpty()) {
                document.add(paragraph);
            }
        }
    }

    private void convertElement(Element element, Paragraph para) {
        if (element.children().size() == 0 && !element.text().isEmpty()) {
            para.add(new Chunk(element.text()));
        }
        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                para.add(new Chunk(((TextNode) child).text()));
            } else if (child instanceof Element) {
                Element elem = (Element)child;
                if (SHOULD_SKIP.contains(elem.tagName())) {
                    continue;
                }
                if (elem.tagName().equalsIgnoreCase("a")) {
                    Anchor anchor = new Anchor(elem.text());
                    Font font = anchor.getFont();
                    font.setColor(Color.BLUE);
                    anchor.setReference(child.attr("href"));
                    anchor.setFont(font);
                    para.add(anchor);
                } else {
                    para.add(new Chunk(elem.text()));
                }
            }
        }
    }
}
