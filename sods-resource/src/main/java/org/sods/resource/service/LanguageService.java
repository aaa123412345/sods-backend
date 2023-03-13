package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.LanguageMatrix;

import java.util.List;

public interface LanguageService {
    ResponseResult getAllLanguagesSimpleForm();
    ResponseResult getAllLanguagesFullFormWithSimpleForm(String languageSimpleForm);

    ResponseResult getAllLanguagesFullFormWithTheseLanguages();

    ResponseResult getFullMatrix();

    ResponseResult updateFullMatrix(List<LanguageMatrix> newLanguageMatrix);

    ResponseResult insertLanguages(String language, List<LanguageMatrix> newLanguageMatrix);

    ResponseResult removeLanguages(String language, List<LanguageMatrix> newLanguageMatrix);
}
