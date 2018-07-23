package com.mytaxi.android_demo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class TestUtils {

    private static final String LOGIN = "login";
    private static final String USERNAME = "username";
    private static final String URL = "https://randomuser.me/api/?seed=a1f30d446f820665";
    private static final String RESULTS = "results";
    private static final String PASSWORD = "password";

    private static String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> getValidCredentials() {
        List<String> credentials = new ArrayList<String>();

        //Connect to the URL with the valid user credentials
        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream in = null;
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get the String from the InputStream
            String jsonResponse = readStream(in);

            try {
                // Extract only the username and the password from the received json
                JSONObject obj = new JSONObject(jsonResponse);
                String username = obj.getJSONArray(RESULTS).getJSONObject(0).getJSONObject(LOGIN).getString(USERNAME);
                String password = obj.getJSONArray(RESULTS).getJSONObject(0).getJSONObject(LOGIN).getString(PASSWORD);
                // Put the valid username and password into a List
                credentials.add(username);
                credentials.add(password);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        } finally {
            urlConnection.disconnect();
            // Return the List with two elements: username and password
            return credentials;
        }
    }
}
