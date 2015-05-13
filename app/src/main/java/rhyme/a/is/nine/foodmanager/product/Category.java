package rhyme.a.is.nine.foodmanager.product;

/**
 * Created by Matthias on 13.05.2015.
 */
public class Category {
    private String name;
    private int best_before_days;

    public Category(String name, int best_before_days)
    {
        this.name = name;
        this.best_before_days = best_before_days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBestBeforeDays() {
        return best_before_days;
    }

    public void setBestBeforeDays(int best_before_days) {
        this.best_before_days = best_before_days;
    }

    @Override
    public String toString() {
        return name;
    }
}
