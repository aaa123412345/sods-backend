package org.sods.websocket.service;

import org.sods.websocket.domain.Message;

public interface VotingService {
    Message join(Message message);
    Message message(Message message);
    Message leave(Message message);
}
