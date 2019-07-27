package com.example.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();
    private QueryUtils(){

    }
    public static List<News> fetchNewsData(String queryString){
        URL url = createUrl(queryString);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "IOException ", e);
        }
        List<News> newsList = extractResultFromJsonData(jsonResponse);
        return newsList;
    }

    private static List<News> extractResultFromJsonData(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for(int i = 0; i< results.length(); i++){
                JSONObject currentNews = results.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String subject = currentNews.getString("sectionName");
                String webUri = currentNews.getString("webUrl");
                String date = currentNews.getString("webPublicationDate");
                newsList.add(new News(subject,title,date, webUri));
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "error extracting data: ", e);
        }

        return newsList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the news JSON result." ,e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String queryString) {
        URL url = null;
        try {
            url = new URL(queryString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }

        return url;
    }
}
