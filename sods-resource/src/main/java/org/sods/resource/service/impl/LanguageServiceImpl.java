package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.RedisCache;
import org.sods.resource.domain.Language;
import org.sods.resource.domain.LanguageMatrix;
import org.sods.resource.mapper.LanguageMapper;
import org.sods.resource.mapper.LanguageMatrixMapper;
import org.sods.resource.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageMapper languageMapper;
    @Autowired
    private LanguageMatrixMapper languageMatrixMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult getAllLanguagesSimpleForm() {
        //get all items in languageMapper
        List<Language> languages = languageMapper.selectList(null);
        List<String> languagesString = new ArrayList<>();
        languages.forEach(e->{
            languagesString.add(e.getLanguageSimpleForm());
                });
        Map<String,Object> map = new HashMap<>();
        map.put("languageData", languagesString);

        return new ResponseResult<>(HttpStatus.OK.value(), "success", map);



    }

    @Override
    public ResponseResult getAllLanguagesFullFormWithSimpleForm(String languageSimpleForm) {
        QueryWrapper<LanguageMatrix> languageMatrixQueryWrapper = new QueryWrapper<>();
        languageMatrixQueryWrapper.eq("lsf_in", languageSimpleForm);

        List<LanguageMatrix> languageMatrixList = languageMatrixMapper.selectList(languageMatrixQueryWrapper);
        List<String> languagesString = new ArrayList<>();
        languageMatrixList.forEach(e->{
            languagesString.add(e.getLanguageFull());
        });
        Map<String,Object> map = new HashMap<>();
        map.put("languageIn", languageSimpleForm);
        map.put("languageData", languagesString);
        return new ResponseResult<>(HttpStatus.OK.value(), "success", map);

    }

    @Override
    public ResponseResult getAllLanguagesFullFormWithTheseLanguages() {
        List<Language> languages = languageMapper.selectList(null);
        Map<String, String> languageMap = new HashMap<>();
        languages.forEach(e->{
            QueryWrapper<LanguageMatrix> languageMatrixQueryWrapper = new QueryWrapper<>();
            languageMatrixQueryWrapper.eq("lsf_basic",e.getLanguageSimpleForm());
            languageMatrixQueryWrapper.eq("lsf_in", e.getLanguageSimpleForm());
            LanguageMatrix languageMatrix = languageMatrixMapper.selectOne(languageMatrixQueryWrapper);
            languageMap.put(e.getLanguageSimpleForm(), languageMatrix.getLanguageFull());
        });

        return new ResponseResult<>(HttpStatus.OK.value(), "success", languageMap);


    }

    @Override
    public ResponseResult getFullMatrix() {
        List<LanguageMatrix> languageMatrixList = languageMatrixMapper.selectList(null);

        return new ResponseResult<>(HttpStatus.OK.value(), "success", languageMatrixList);
    }


    @Override
    public ResponseResult updateLanguages(List<String> languages, List<LanguageMatrix> newLanguageMatrix) {
        updateLanguage(languages);
        updateLanguageMatrix(newLanguageMatrix);
        redisCache.deleteObject("CACHE:language:/rest/language/simpleform");

        return new ResponseResult<>(HttpStatus.OK.value(), "success");
    }




    private void updateLanguage(List<String> languages){
        languageMapper.delete(null);
        languages.forEach(e->{
            Language n = new Language(e);
            languageMapper.insert(n);
        });
    }

    private void updateLanguageMatrix(List<LanguageMatrix> newLanguageMatrix){

        //delete every item in languageMatrixMapper
        languageMatrixMapper.delete(null);
        //insert new items
        newLanguageMatrix.forEach(e->{
            languageMatrixMapper.insert(e);
        });

    }


}
