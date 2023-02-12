package org.sods.websocket.service;

import org.sods.websocket.domain.VotingState;

public interface WebSocketRedisService {
    VotingState getVotingStateWithPasscode(String passcode);

    Boolean setVotingStateWithPasscode(String passcode,VotingState votingState);

    Boolean setObjectIfKeyNotExist(String key,Object object);
    Boolean deleteObjectIfKeyExist(String key);

    Boolean deleteVotingGroup(String passcode);

    Boolean checkIfKeyExist(String key);
}
