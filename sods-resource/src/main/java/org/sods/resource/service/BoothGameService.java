package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BoothGame;

public interface BoothGameService {

    ResponseResult createBoothGame(BoothGame boothGame);

    ResponseResult getAllBoothGame();

    ResponseResult getBoothGameByBoothId(Integer id);

    ResponseResult getBoothGameByGameId(Integer id);

    ResponseResult updateBoothGameByGameIdAndBoothId(Integer boothId, Integer gameId, BoothGame boothGame);

    ResponseResult deleteFloorPlanByBoothIdAndGameId(Integer boothId, Integer gameId);

}
