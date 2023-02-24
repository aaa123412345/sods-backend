package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Treasure;

public interface TreasureService {

    ResponseResult createARTreasure(Treasure treasure);

    ResponseResult getAllARTreasure();

    ResponseResult getARTreasureById(Integer id);

    ResponseResult updateARTreasureById(Integer id, Treasure treasure);

    ResponseResult deleteARTreasure(Integer id);

}
