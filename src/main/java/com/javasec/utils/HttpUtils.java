package com.javasec.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class HttpUtils {
    public static void doPOST(byte[] obj,String address) throws Exception{
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Content-Type", "text/plain");
        URI url = new URI(address);
        HttpEntity<byte[]> requestEntity = new HttpEntity (obj,requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println(res.getBody());
    }
    public static void doPostWithParams(String params,String address) throws Exception{
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        URI url = new URI(address);
        HttpEntity<byte[]> requestEntity = new HttpEntity (params,requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println(res.getBody());
    }
}
