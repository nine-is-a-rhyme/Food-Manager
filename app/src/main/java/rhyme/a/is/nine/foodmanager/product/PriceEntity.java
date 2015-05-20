package rhyme.a.is.nine.foodmanager.product;

import java.util.Date;

/**
 * Created by martinmaritsch on 20/05/15.
 */
public class PriceEntity {

    private String name = null;
    private double price = -1;
    private Date buyDate = null;

    public PriceEntity()
    {

    }

    public PriceEntity(String name, double price, Date buyDate) {
        this.name = name;
        this.price = price;
        this.buyDate = buyDate;
    }

    public PriceEntity(String name, double price) {
        this.name = name;
        this.price = price;
        this.buyDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
