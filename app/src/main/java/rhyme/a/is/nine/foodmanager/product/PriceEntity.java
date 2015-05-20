package rhyme.a.is.nine.foodmanager.product;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by martinmaritsch on 20/05/15.
 */
public class PriceEntity implements Serializable {

    static final long serialVersionUID = 2338835581233773842L;


    private String name = null;
    private float price = -1;
    private Date buyDate = null;

    public PriceEntity() {

    }

    public PriceEntity(String name, float price, Date buyDate) {
        this.name = name;
        this.price = price;
        this.buyDate = buyDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
