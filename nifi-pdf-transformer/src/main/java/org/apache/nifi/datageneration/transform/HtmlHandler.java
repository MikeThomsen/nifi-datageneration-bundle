package org.apache.nifi.datageneration.transform;

import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
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

import static org.apache.nifi.datageneration.transform.HtmlUtils.countHeaders;
import static org.apache.nifi.datageneration.transform.HtmlUtils.getHeaders;
import static org.apache.nifi.datageneration.transform.HtmlUtils.getMainCells;

public class HtmlHandler implements PdfHandler {
    public static String CSS_SELECTOR_ATTRIBUTE = "css.selector";
    public static final List<String> SHOULD_SKIP = Arrays.asList(
        "script", "link", "style"
    );

    @Override
    public void handle(Document document, byte[] input, Map<String, String> attributes) throws Exception {
        String content = new String(input);
        org.jsoup.nodes.Document soupDocument = Jsoup.parse(content);
        Elements body = soupDocument.select("body");
        if (attributes.containsKey(CSS_SELECTOR_ATTRIBUTE) && !attributes.get(CSS_SELECTOR_ATTRIBUTE).isEmpty()) {
            handleCssSelector(document, soupDocument, attributes.get(CSS_SELECTOR_ATTRIBUTE));
        } else if (body.size() > 0) {
            handleCssSelector(document, soupDocument, "body > *");
        } else if (body.size() == 0) {
            handleCssSelector(document, soupDocument, "*");
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
            convertElement(element, document, paragraph);
            if (!paragraph.isEmpty()) {
                document.add(paragraph);
            }
        }
    }

    private void convertElement(Element element, Document document, Paragraph para) {
        if (element.children().size() == 0 && !element.text().isEmpty()) {
            para.add(new Chunk(element.text()));
        }
        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                para.add(new Chunk(((TextNode) child).text()));
            } else if (child instanceof Element) {
                Element elem = (Element)child;
                String name = elem.tagName();
                if (SHOULD_SKIP.contains(elem.tagName())) {
                    continue;
                }
                if (name.equalsIgnoreCase("a")) {
                    Anchor anchor = new Anchor(elem.text());
                    Font font = anchor.getFont();
                    font.setColor(Color.BLUE);
                    anchor.setReference(child.attr("href"));
                    anchor.setFont(font);
                    para.add(anchor);
                } else if (name.equalsIgnoreCase("table")) {
                    handleTable(document, elem);
                } else {
                    para.add(new Chunk(elem.text()));
                }
            }
        }
    }

    private void handleTable(Document document, Element table) {
        int columnCount = countHeaders(table);
        try {
            Table generated = new Table(columnCount);
            List<String> headers = getHeaders(table);
            addHeaders(headers, generated);
            List<List<String>> cells = getMainCells(table);
            addMainCells(cells, generated, columnCount);
            
            document.add(generated);
        } catch (DocumentException e) {
            throw new ProcessException(e);
        }
    }

    private void addHeaders(List<String> headers, Table generated) throws BadElementException {
        if (!headers.isEmpty()) {
            for (int x = 0; x < headers.size(); x++) {
                Cell cell = new Cell(new Chunk(headers.get(x)));
                cell.setBorder(3);
                generated.addCell(cell);
            }
        }
    }

    private void addMainCells(List<List<String>> cells, Table generated, int columnCount) throws BadElementException {
        for (int x = 0; x < cells.size(); x++) {
            List<String> row = cells.get(x);
            for (int y = 0; y < row.size(); y++) {
                Cell cell = new Cell(new Chunk(row.get(y)));
                generated.addCell(cell);
            }

            if (row.size() < columnCount) {
                int delta = columnCount - row.size();
                for (int y = 0; y < delta; y++) {
                    generated.addCell("");
                }
            }
        }
    }
}
