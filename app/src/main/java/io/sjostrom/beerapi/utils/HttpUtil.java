package io.sjostrom.beerapi.utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Nick on 12/1/2015.
 */
public class HttpUtil {

    public static String POST = "POST";
    public static String GET = "GET";

    public static String GET(String strUrl){
        return request(HttpUtil.GET, strUrl);
    }

    public static String POST(String strUrl){
        return request(HttpUtil.GET, strUrl);
    }

    public static String request(String method, String strUrl) {
        InputStream inputStream = null;
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod(method);

            // receive response as inputStream
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            // convert inputstream to string
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
                //inputStream.close();
            }
            else
                result = "Error";

        } catch (Exception e) {
            Log.d("request", e.getLocalizedMessage());
            result = "Something went wrong !";
        }
        finally {
            if(urlConnection != null) urlConnection.disconnect();
        }

        return result;
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
