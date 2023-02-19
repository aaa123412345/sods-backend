package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARTreasure;

public interface ARTreasureService {

    ResponseResult createARTreasure(ARTreasure arTreasure);

    ResponseResult getAllARTreasure();

    ResponseResult getARTreasureById(Integer id);

    ResponseResult updateARTreasureById(Integer id, ARTreasure arTreasure);

    ResponseResult deleteARTreasure(Integer id);

}
