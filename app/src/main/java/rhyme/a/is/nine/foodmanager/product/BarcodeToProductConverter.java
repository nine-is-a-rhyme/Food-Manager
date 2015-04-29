package rhyme.a.is.nine.foodmanager.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rhyme.a.is.nine.foodmanager.database.DatabaseAccess;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class BarcodeToProductConverter {

    private static final String REQUEST_URL = "http://www.codecheck.info/product.search?q=";

    public static Product getProductForBarcode(String barcode) {

        if (barcode == null)
            return null;

        Product product = DatabaseAccess.getDatabaseProductByBarcode(barcode);

        if(product != null)
            return product;

        String webContent = "";
        ConnectionTask ct = new ConnectionTask();
        try {
            webContent = ct.execute(REQUEST_URL + barcode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if(webContent == null || webContent == "")
            return null;

        String name = getProductName(webContent);
        String category = getProductCategory(webContent);
        String size = getProductSize(webContent);

        if(name != null)
            return new Product(name, category, barcode, size, 1, ProductPlace.FRIDGE);
        else
            return null;
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
        Pattern pattern = Pattern.compile("<p class=\"product-info-label\">Menge \\/ Grösse<\\/p>[ \\n\\r\\t]*?<p>(.*?)<\\/p>");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
            return matcher.group(1);

        return null;
    }
}
