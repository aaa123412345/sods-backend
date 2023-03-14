package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.LanguageMatrix;

import java.util.List;

public interface LanguageService {
    ResponseResult getAllLanguagesSimpleForm();
    ResponseResult getAllLanguagesFullFormWithSimpleForm(String languageSimpleForm);

    ResponseResult getAllLanguagesFullFormWithTheseLanguages();

    ResponseResult getFullMatrix();

    ResponseResult updateLanguages(List<String> languages,List<LanguageMatrix> newLanguageMatrix);

}
