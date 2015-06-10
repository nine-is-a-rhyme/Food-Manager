package rhyme.a.is.nine.foodmanager.product;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jan on 29.04.2015.
 */
public class ConnectionTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
            if(connection.getResponseCode() != 200) {
                throw new Exception("Failed to connect");
            }

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            BufferedReader in = new BufferedReader(reader);

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
