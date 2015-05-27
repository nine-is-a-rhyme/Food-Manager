package rhyme.a.is.nine.foodmanager.database;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rhyme.a.is.nine.foodmanager.product.Product;

/**
 * Created by martinmaritsch on 29/04/15.
 */
public class Database<T> implements Serializable {

    protected List<T> list;

    protected String fileName;


    public Database(String fileName) {
        this.fileName = fileName;
        this.list = new ArrayList<T>();
    }


    public void writeToFile(Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(this.fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(this.list);
            outputStream.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(Context context) {
        Object object = null;
        FileInputStream outputStream;
        try {
            outputStream = context.openFileInput(this.fileName);
            ObjectInputStream ois = new ObjectInputStream(outputStream);
            object = ois.readObject();
            outputStream.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(object != null)
            this.list = (List<T>) object;
    }
}
