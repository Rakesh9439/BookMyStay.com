package com.auth_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="TRANSLATION-SERVICE")
public interface TranslationClient {


    @GetMapping("/api/v1/translate")
    String translate(
            @RequestParam String text,
            @RequestParam String sourceLang,
            @RequestParam String targetLang
    );


}
