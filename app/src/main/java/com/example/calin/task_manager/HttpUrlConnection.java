package com.example.calin.task_manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}

public class HttpUrlConnection extends Thread {
    private static final String URL = "http://192.168.0.187:3000/api/v1/users/test";
    private static Map<String, String> parametre = new HashMap<String, String>();

    public HttpUrlConnection(Map<String, String> parametre) {
        this.parametre = parametre;
    }

    // TODO: Read the response in the right form

    static Thread getThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL obj = new URL(URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } else {
                    System.out.println("GET request not worked");
                }
            } catch(IOException e) {
                System.out.println("\n\nERROR: ");
                System.out.println(e);
            }
        }
    });

    static Thread postThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL url = new URL(URL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parametre));
                out.flush();
                out.close();

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // TODO: Toast for success
                } else {
                    System.out.println("POST request not worked");
                }
            } catch(IOException e) {
                System.out.println("\n\nERROR: ");
                System.out.println(e);
            }
        }
    });
}
