package ru.netology;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=cPfCV2peRlGcywjabYLJVChKPwtZA8Fa4sQWzhwA");
        CloseableHttpResponse response = httpClient.execute(request);
        Post post = mapper.readValue(response.getEntity().getContent(), Post.class);
        System.out.println(post);
        HttpGet request2 = new HttpGet(post.getUrl());
        CloseableHttpResponse response2 = httpClient.execute(request2);
        String[] arr = post.getUrl().split("/");
        String file = arr[arr.length - 1];
        HttpEntity entity = response2.getEntity();
        if (entity != null) {
            FileOutputStream fos = new FileOutputStream(file);
            entity.writeTo(fos);
            fos.close();


        }
    }
}