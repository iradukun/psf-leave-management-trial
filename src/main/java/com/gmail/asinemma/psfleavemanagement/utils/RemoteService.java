package com.gmail.asinemma.psfleavemanagement.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RemoteService {

    public String send(String url, Map<String, String> params, String method, Object object) {
        System.out.println("Object and url (" + object + "," + url + ") ");
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            if (params != null) {
                for (String key : params.keySet()) {
                    headers.add(key, params.get(key));
                }
            } else {
                headers.add("Content-Type", "application/json");
            }

            HttpEntity<Object> entity = new HttpEntity<>(object, headers);

            ResponseEntity<String> responseEntity = restTemplate
                    .exchange(url,
                            method.equalsIgnoreCase("GET") ? HttpMethod.GET
                                    : method.equalsIgnoreCase("PUT") ? HttpMethod.PUT : HttpMethod.POST,
                            entity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T send(String url, Map<String, String> params, String method, Object object, Class returnClassName) {
        return toObject(send(url, params, method, object), returnClassName);
    }

    public <T> T toObject(Object object, Class className) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return (T) mapper.readValue(object.toString(), className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
