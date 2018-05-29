package edu.mum.cs490.project.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
public class HttpSender {

    private String url;
    private String user;
    private String pass;
    private String contentType;
    private Integer connTime;
    private Integer readTime;

    public HttpSender(String url, String user, String pass, String contentType, Integer connTime, Integer readTime) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.contentType = contentType;
        this.connTime = connTime;
        this.readTime = readTime;
    }

    public String doGet(Map<String, String> requestMap) {
        return null;
    }

    public String doPostTransactionToApi(String data) throws IOException {
        return doPost(data);
    }

    public String doPost(String data) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // request header
        con.setRequestMethod("POST");
        con.setConnectTimeout(connTime);
        con.setReadTimeout(readTime);
        setBasicAuthenticaion(con);
        con.setRequestProperty("Content-type", contentType);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(data.getBytes());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Sending POST request to URL : " + url);
        System.out.println("Request Data : " + data);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response Data  : " + response.toString());
        return response.toString();
    }

    private void setBasicAuthenticaion(HttpURLConnection con) {
        if (user != null && !user.isEmpty() && pass != null && !pass.isEmpty()) {
            String userCredentials = user + ":" + pass;
            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
            con.setRequestProperty("Authorization", basicAuth);
        }
    }
}
