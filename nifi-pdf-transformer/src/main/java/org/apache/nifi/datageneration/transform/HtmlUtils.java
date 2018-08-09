package org.apache.nifi.datageneration.transform;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlUtils {
    public static int countHeaders(Element table) {
        Elements headers = table.select("th");
        if (!headers.isEmpty()) {
            return headers.size();
        }
        Elements trs = table.select("tr");
        int tdCount = 0;
        for (int x = 0; x < trs.size(); x++) {
            Element tr = trs.get(x);
            int count = tr.select("td").size();
            tdCount = count > tdCount ? count : tdCount;
        }

        return tdCount;
    }

    public static List<String> getHeaders(Element table) {
        List<String> headers = new ArrayList<>();
        Elements ths = table.select("th");
        for (int x = 0; x < ths.size(); x++) {
            headers.add(ths.get(x).text().trim());
        }

        return headers;
    }

    public static List<List<String>> getMainCells(Element table) {
        List<List<String>> cells = new ArrayList<>();

        Elements trs = table.select("tbody > tr");
        if (trs.isEmpty()) {
            trs = table.select("> tr");
        }

        for (int x = 0; x < trs.size(); x++) {
            Element tr = trs.get(x);
            Elements tds = tr.select("td");
            List<String> _temp = new ArrayList<>();
            if (!tds.isEmpty()) {
                for (int y = 0; y < tds.size(); y++) {
                    _temp.add(tds.get(y).text().trim());
                }
            }
            cells.add(_temp);
        }

        return cells;
    }
}
