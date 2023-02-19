package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARAnswer;

public interface ARAnswerService {

    ResponseResult createARAnswer(ARAnswer arAnswer);

    ResponseResult getAllARAnswer();

    ResponseResult getARAnswerById(Integer id);

    ResponseResult getARAnswerByTreasureId(Integer id);

    ResponseResult deleteARAnswerById(Integer id);

}
