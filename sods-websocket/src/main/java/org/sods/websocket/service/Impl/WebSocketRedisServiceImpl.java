package org.sods.websocket.service.Impl;

import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.WebSocketRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.sods.common.utils.RedisCache;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
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

    @Override
    public Boolean setObjectIfKeyNotExist(String key, Object object) {
        if(Objects.isNull(redisCache.getCacheObject(key))){
            redisCache.setCacheObject(key,object);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteObjectIfKeyExist(String key) {
        if(!Objects.isNull(redisCache.getCacheObject(key))){
            redisCache.deleteObject(key);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkIfKeyExist(String key) {
        if(!Objects.isNull(redisCache.getCacheObject(key))){
            return true;
        }
        return false;
    }
}
