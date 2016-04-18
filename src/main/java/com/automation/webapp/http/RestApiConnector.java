package com.automation.webapp.http;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestApiConnector {
    private static final String USER_AGENT = "Mozilla/5.0";

    private String response;
    private int statusCode;
    private StringEntity postingString;
    private Map<String, String> urlParameters = new HashMap<>();

    public void doCall(String url, HttpMethod httpMethod) throws IOException {
        if(httpMethod.equals(HttpMethod.GET)) {
            sendGET(url);
        } else if(httpMethod.equals(HttpMethod.POST)) {
            sendPOST(url);
        }
    }

    private void sendGET(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet http = new HttpGet(url);
        for (Map.Entry<String, String> entry : urlParameters.entrySet())
        {
            http.addHeader(entry.getKey(), entry.getValue());
        }

        http.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(http);

        statusCode = httpResponse.getStatusLine().getStatusCode();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            stringBuffer.append(inputLine);
        }
        reader.close();

        response = stringBuffer.toString();
        httpClient.close();
    }

    private void sendPOST(String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.setEntity(postingString);
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        statusCode = httpResponse.getStatusLine().getStatusCode();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            stringBuffer.append(inputLine);
        }
        reader.close();

        response = stringBuffer.toString();
        httpClient.close();

    }

    public StringEntity getPostingString() {
        return postingString;
    }

    public void setPostingString(StringEntity postingString) {
        this.postingString = postingString;
    }

    public Map<String, String> getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(Map<String, String> urlParameters) {
        this.urlParameters = urlParameters;
    }

    public String getResponse() {
        return response;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
