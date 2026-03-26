package com.translation_service.controller;


import com.translation_service.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translate")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public String translate(
            @RequestParam String text,
            @RequestParam String sourceLang,
            @RequestParam String targetLang) {

        return translationService.translate(text, sourceLang, targetLang);
    }

}
