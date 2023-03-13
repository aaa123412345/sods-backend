package org.sods.resource.Controller;

import org.sods.common.annotation.RedisCacheable;
import org.sods.resource.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/language")
public class LanguagesController {

    @Autowired
    private LanguageService languageService;

    @RedisCacheable(key = "language",expire = "4000")
    @GetMapping("/simpleform")
    public Object getAllLanguagesSimpleForm() {
        return languageService.getAllLanguagesSimpleForm();
    }

    @RedisCacheable(key = "language",expire = "4000")
    @GetMapping("/fullform/{languageSimpleForm}")
    public Object getAllLanguagesFullFormWithSimpleForm(@PathVariable("languageSimpleForm")String languageSimpleForm) {
        return languageService.getAllLanguagesFullFormWithSimpleForm(languageSimpleForm);
    }

    @RedisCacheable(key = "language",expire = "4000")
    @GetMapping("/fullform")
    public Object getAllLanguagesFullFormWithTheseLanguages() {
        return languageService.getAllLanguagesFullFormWithTheseLanguages();
    }

}
