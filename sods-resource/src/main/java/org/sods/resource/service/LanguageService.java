package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface LanguageService {
    ResponseResult getAllLanguagesSimpleForm();
    ResponseResult getAllLanguagesFullFormWithSimpleForm(String languageSimpleForm);

    ResponseResult getAllLanguagesFullFormWithTheseLanguages();
}
