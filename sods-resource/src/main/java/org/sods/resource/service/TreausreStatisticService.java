package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.TreasureStatistic;

public interface TreausreStatisticService {

    ResponseResult createARStatistic(TreasureStatistic treasureStatistic);

    ResponseResult getAllARStatistic();

    ResponseResult getARStatisticById(Long id);

}
