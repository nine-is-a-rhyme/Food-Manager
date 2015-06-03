package rhyme.a.is.nine.foodmanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by David on 03.06.2015.
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
  ImageView image;

  public DownloadImage(ImageView image){
    this.image = image;
  }

  @Override
  protected Bitmap doInBackground(String... params) {
    String urldisplay = params[0];
    Bitmap bitmap = null;

    try {
      InputStream in = new java.net.URL(urldisplay).openStream();
      bitmap = BitmapFactory.decodeStream(in);
    } catch(Exception e){
      e.printStackTrace();
    }
    return bitmap;
  }

  protected void onPostExecute(Bitmap result){
    image.setImageBitmap(result);
  }
}
