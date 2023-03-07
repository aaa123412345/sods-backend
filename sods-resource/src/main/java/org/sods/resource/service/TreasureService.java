package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Treasure;

public interface TreasureService {

    ResponseResult createARTreasure(Treasure treasure);

    ResponseResult getAllARTreasure(Integer deleteFlag);

    ResponseResult getARTreasureById(Long id);

    ResponseResult updateARTreasureById(Long id, Treasure treasure);

    ResponseResult deleteARTreasure(Long id);

}
