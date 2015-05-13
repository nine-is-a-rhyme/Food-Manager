package rhyme.a.is.nine.foodmanager.product;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rhyme.a.is.nine.foodmanager.gui.MainActivity;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class BarcodeToProductConverter {

    private static final String REQUEST_URL = "http://www.codecheck.info/product.search?q=";

    public static class ScannedProduct extends Product {
        String[] categories;

        public ScannedProduct(String name, String[] categories, String barcode, String size, int count) {
            super(name, null, barcode, size, count);
            this.categories = categories;
        }

        public String[] getCategories() {
            return categories;
        }
    }

    public static Product getProductForBarcode(String barcode) {

        if (barcode == null)
            return null;

        Product product = MainActivity.historyDatabase.getProductByBarcode(barcode);

        if(product != null) {
            product.setCount(1);
            return product;
        }


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
        String[] categories = getProductCategories(webContent);
        String size = getProductSize(webContent);

        if(name != null)
            return new ScannedProduct(name, categories, barcode, size, 1);
        else
            return null;
    }

    private static String getProductName(String content) {
        Pattern pattern = Pattern.compile("<meta property=\"og:title\" content=\"(.*?)\"");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
        {
            String str = matcher.group(1);
            return parseUnicode(str);
        }


        return null;
    }

    private static String[] getProductCategories(String content) {
        Pattern pattern = Pattern.compile("pathList = \\[\"(.*?)\"\\]");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
        {
            String[] parsedCategories =  matcher.group(1).split("\", \"");
            for(int i = 0; i<parsedCategories.length; ++i) {
                parsedCategories[i]=parseUnicode(parsedCategories[i]);
            }
            return parsedCategories;
        }


        return null;
    }

    private static String getProductSize(String content) {
        Pattern pattern = Pattern.compile("<p class=\"product-info-label\">Menge \\/ Grösse<\\/p>[ \\n\\r\\t]*?<p>(.*?)<\\/p>");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find())
        {
            String str = matcher.group(1);
            return parseUnicode(str);
        }

        return null;
    }

    public static String parseUnicode(String str)
    {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\\') {
                if (chars.length > i + 5 && chars[i + 1] == 'u') {
                    try{
                        char x = (char) Integer.parseInt(str.substring(i+2, i+6), 16);
                        sb.append(x);
                        i = i + 5;
                    } catch(NumberFormatException e){
                        //not a hex encoding
                        sb.append(chars[i]);
                    }
                } else if (chars.length > i + 1 && chars[i + 1] == 'n') {
                    sb.append('\n');
                    i++;
                } else if (chars.length > i + 1 && chars[i + 1] == 'r') {
                    sb.append('\r');
                    i++;
                }  else {
                    sb.append(chars[i]);
                }
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
