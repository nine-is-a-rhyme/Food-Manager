package rhyme.a.is.nine.foodmanager.product;

import java.util.Date;

/**
 * Created by martinmaritsch on 22/04/15.
 */
public class Product {
    private String name = null;
    private String category = null;
    private String barcode = null;
    private String size = null;
    private Date bestBeforeDate = null;

    public Product(String name, String category, String barcode, String size, Date bestBeforeDate) {
        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.size = size;
        this.bestBeforeDate = bestBeforeDate;
    }

    public Product(String name, String category, String barcode, String size) {
        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getBestBeforeDate() {
        return bestBeforeDate;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }
}
