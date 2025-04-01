package com.round3.realestate.util;

import com.round3.realestate.payload.property.PropertyResultDataDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IdealistaUtils {

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.put("Accept-Language", "en-GB,en;q=0.5");
        headers.put("Host", "www.idealista.com");
        headers.put("Priority", "u=0, i");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:134.0) Gecko/20100101 Firefox/134.0");

        return headers;
    }

    public static PropertyResultDataDto scrap(String url) throws IOException {

        var connection = Jsoup
            .connect(url)
            .headers(getHeaders());

        var doc = connection.get();
        var features = getFeaturesFirstChild(
            doc.getElementsByAttributeValue("class", "info-features")
        );

        return PropertyResultDataDto
            .builder()
            .fullTitle(getFullTitle(doc))
            .price(getPrice(doc))
            .location(getLocation(doc))
            .rooms(getRooms(features))
            .size(getSize(features))
            .type(getType(doc))
            .url(url)
            .build();
    }

    private static String getFullTitle(Document doc) {
        Elements title = doc.getElementsByAttributeValue("class", "main-info__title-main");
        title.getFirst();
        title.getFirst().text();
        return title.getFirst().text().trim();
    }

    private static Elements getFeaturesFirstChild(Elements features) {
        if (features != null && features.first() != null && features.first().children() != null) {
            return features.first().children();
        }
        return null;
    }

    private static String getSize(Elements children) {
        if (children != null && !children.isEmpty()) {
            return children.getFirst().text();
        }
        return null;
    }

    private static String getRooms(Elements children) {
        if (children != null && children.size() > 1) {
            return children.get(1).text();
        }
        return null;
    }

    private static String getPrice(Document doc) {
        var price = doc.getElementsByAttributeValue("class", "info-data-price");
        price.getFirst();
        if (price.getFirst().firstElementChild() != null) {
            return price.getFirst().firstElementChild().text();
        }
        return null;
    }

    private static String getLocation(Document doc) {
        Elements location = doc.getElementsByAttributeValue("class", "main-info__title-minor");
        location.getFirst();
        return location.getFirst().text();
    }

    private static String getType(Document doc) {
        var title = getFullTitle(doc);
        return title.split(" ")[0];
    }
}
