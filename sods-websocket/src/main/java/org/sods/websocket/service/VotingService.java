package org.sods.websocket.service;

import org.sods.websocket.domain.Message;

import java.security.Principal;

public interface VotingService {
    Message joinChannel(Message message, Principal principal);
    Message messageChannel(Message message, Principal principal);
    Message leaveChannel(Message message, Principal principal);
    Message commandForChannel(Message message, Principal principal);


}
