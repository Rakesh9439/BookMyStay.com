package com.translation_service.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {

    private static final String API =
            "https://libretranslate.de/translate";

    public String translate(String text,
                            String source,
                            String target){

        RestTemplate restTemplate = new RestTemplate();

        Map<String,String> body = new HashMap<>();
        body.put("q", text);
        body.put("source", source);
        body.put("target", target);
        body.put("format", "text");

        Map response = restTemplate.postForObject(API, body, Map.class);

        return (String) response.get("translatedText");
    }
}
