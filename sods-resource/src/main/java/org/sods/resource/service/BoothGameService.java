package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BoothGame;

public interface BoothGameService {

    ResponseResult createBoothGame(BoothGame boothGame);

    ResponseResult getAllBoothGame();

    ResponseResult getBoothGameByBoothId(Long id);

    ResponseResult getBoothGameByGameId(Long id);

    ResponseResult updateBoothGameByGameIdAndBoothId(Long boothId, Long gameId, BoothGame boothGame);

    ResponseResult deleteFloorPlanByBoothIdAndGameId(Long boothId, Long gameId);

}
