package org.sods.resource.Controller;

import org.sods.common.annotation.RedisCacheable;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.LanguageMatrix;
import org.sods.resource.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/language")
public class LanguagesController {

    @Autowired
    private LanguageService languageService;

    @RedisCacheable(key = "language",expire = "4000")
    @GetMapping("/simpleform")
    public ResponseResult getAllLanguagesSimpleForm() {
        return languageService.getAllLanguagesSimpleForm();
    }

    @RedisCacheable(key = "language",expire = "4000")
    @GetMapping("/fullform/{languageSimpleForm}")
    public ResponseResult getAllLanguagesFullFormWithSimpleForm(@PathVariable("languageSimpleForm")String languageSimpleForm) {
        return languageService.getAllLanguagesFullFormWithSimpleForm(languageSimpleForm);
    }

    @RedisCacheable(key = "language",expire = "4000")
    @GetMapping("/fullform")
    public ResponseResult getAllLanguagesFullFormWithTheseLanguages() {
        return languageService.getAllLanguagesFullFormWithTheseLanguages();
    }

    @GetMapping("/fullMatrix")
    public ResponseResult getFullMatrix() {
        return languageService.getFullMatrix();
    }

    @PostMapping("/fullMatrix")
    public ResponseResult insertLanguages(@RequestParam("insert")String language, @RequestBody List<LanguageMatrix> newLanguageMatrix) {
        return languageService.insertLanguages(language, newLanguageMatrix);
    }

    @DeleteMapping("/fullMatrix")
    public ResponseResult removeLanguages(@RequestParam("delete")String language, @RequestBody List<LanguageMatrix> newLanguageMatrix) {
        return languageService.removeLanguages(language, newLanguageMatrix);
    }

    @PutMapping("/fullMatrix")
    public ResponseResult updateFullMatrix(@RequestBody List<LanguageMatrix> newLanguageMatrix) {
        return languageService.updateFullMatrix(newLanguageMatrix);
    }

}
