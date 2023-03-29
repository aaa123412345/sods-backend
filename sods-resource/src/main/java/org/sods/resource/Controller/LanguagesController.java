package org.sods.resource.Controller;

import org.sods.common.annotation.RedisCacheable;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.LanguageMatrix;
import org.sods.resource.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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



    @PreAuthorize("@ex.hasAuthority('system:cms:put')")
    @PutMapping("/languagesAndMatrix")
    public ResponseResult updateLanguages(@RequestParam List<String> languages,
                                          @RequestBody List<LanguageMatrix> newLanguageMatrix) {
        return languageService.updateLanguages( languages, newLanguageMatrix);
    }

}
