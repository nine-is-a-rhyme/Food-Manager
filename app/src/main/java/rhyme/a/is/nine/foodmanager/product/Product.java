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
    private int count = 0;
    private boolean manual = true;

    public ProductPlace getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(ProductPlace productPlace) {
        this.productPlace = productPlace;
    }

    private ProductPlace productPlace = null;

    public Product() {

    }

    public Product(String name, String category, String barcode, String size, int count, ProductPlace productPlace, Date bestBeforeDate) {
        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.size = size;
        this.bestBeforeDate = bestBeforeDate;
        this.productPlace = productPlace;
        this.count = count;
    }

    public Product(String name, String category, String barcode, String size, int count, ProductPlace productPlace) {
        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.size = size;
        this.productPlace = productPlace;
        this.count = count;
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

    public void increaseCount() { count++; }

    public void decreaseCount() { if(count > 0) count--; }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }
}
