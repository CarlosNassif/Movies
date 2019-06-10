package com.example.hello.victor.movies.services;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIRequest extends AsyncTask<String, Void, String> {

    public static String searchFilms(String url){

        String respRequest = "";

        try {
            URL api = new URL(url);

            int resp;

            HttpURLConnection connection;
            InputStream inputStream;

            connection = (HttpURLConnection) api.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();
            resp = connection.getResponseCode();

            if(resp < HttpURLConnection.HTTP_BAD_REQUEST)
                inputStream = connection.getInputStream();
            else
                inputStream = connection.getErrorStream();

            respRequest = convertInputStreamToString(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  respRequest;

    }

    public  static String convertInputStreamToString(InputStream inputStream){
        StringBuffer stringBuffer = new StringBuffer();

        try {
            BufferedReader br;
            String line;

            br = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = br.readLine()) != null){

                stringBuffer.append(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    @Override
    protected String doInBackground(String... strings) {
        return searchFilms(strings[0]);
    }
}
