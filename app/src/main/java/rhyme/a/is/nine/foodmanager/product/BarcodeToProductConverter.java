package rhyme.a.is.nine.foodmanager.product;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class BarcodeToProductConverter {

    private static final String REQUEST_URL = "http://codecheck.info/product.search?q=";

    public static Product getProductForBarcode(String barcode) {
        String webContent = getFoodApiResponse(barcode);

        String name = getProductName(webContent);
        String category = getProductCategory(webContent);
        String size = getProductSize(webContent);

        return new Product(name, category, barcode, size);
    }

    public static String getFoodApiResponse(String barcode) {
        String webContent = null;
        try {
            Scanner scanner = new Scanner(new URL(REQUEST_URL + barcode).openStream(), "UTF-8").useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (MalformedURLException e) {
            // normally impossible
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static String getProductName(String content) {
        Pattern pattern = Pattern.compile("<meta property=\"og:title\" content=\"(.*?)\"");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
            return matcher.group(1);

        return null;
    }

    private static String getProductCategory(String content) {
        Pattern pattern = Pattern.compile("pathList = \\[\"(.*?)\"\\]");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
            return matcher.group(1).split("\", \"")[2];

        return null;
    }

    private static String getProductSize(String content) {
        Pattern pattern = Pattern.compile("<p class=\"product-info-label\">Menge \\/ Grösse<\\/p>[ \\n\\r\\t]*?<p>(.*)<\\/p>");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
            return matcher.group(1);

        return null;
    }
}
