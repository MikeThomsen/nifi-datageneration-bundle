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
}
