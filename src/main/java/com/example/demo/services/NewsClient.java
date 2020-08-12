package com.example.demo.services;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class NewsClient {


    public String makeGetCall(String api) throws Exception {

        StringBuilder response = new StringBuilder();

        URL url = new URL(api);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            throw new Exception(response.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
      String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }

        conn.disconnect();


        return response.toString();
    }


    public String makeApiCall(String api)
    {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        String body = response.getBody();

        return body;
    }
}
