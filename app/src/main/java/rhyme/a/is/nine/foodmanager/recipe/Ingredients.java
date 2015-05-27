package rhyme.a.is.nine.foodmanager.recipe;

import java.io.Serializable;

/**
 * Created by lalinda on 5/27/15.
 */
public class Ingredients implements Serializable {


    private String amount = null;
    private String name = null;

    public Ingredients() {
    }

    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

}
