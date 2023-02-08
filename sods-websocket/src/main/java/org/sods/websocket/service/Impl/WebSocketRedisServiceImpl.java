package org.sods.websocket.service.Impl;

import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.WebSocketRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.sods.common.utils.RedisCache;

import java.util.Objects;

public class WebSocketRedisServiceImpl implements WebSocketRedisService {
    @Autowired
    private RedisCache redisCache;

    @Override
    public VotingState getVotingStateWithPasscode(String passcode) {
        String RedisPasscode = "Voting:" + passcode;
        return redisCache.getCacheObject(RedisPasscode);
    }

    @Override
    public Boolean setVotingStateWithPasscode(String passcode, VotingState votingState) {
        VotingState votingStateCur = getVotingStateWithPasscode(passcode);
        if(Objects.isNull(votingStateCur)){
            return false;
        }
        
        return null;
    }
}
