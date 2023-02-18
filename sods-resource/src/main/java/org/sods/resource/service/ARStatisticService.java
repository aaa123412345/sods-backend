package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARStatistic;

public interface ARStatisticService {

    ResponseResult createARStatistic(ARStatistic arStatistic);

    ResponseResult getAllARStatistic();

    ResponseResult getARStatisticById(Integer id);

}
