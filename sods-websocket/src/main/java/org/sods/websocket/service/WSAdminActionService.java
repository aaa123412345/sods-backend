package org.sods.websocket.service;

import org.sods.websocket.domain.Message;

import java.security.Principal;

public interface WSAdminActionService {
    Message CLEAR(Message message, Principal principal);
    Message RemoveGroup(Message message, Principal principal);
    Message CreateGroup(Message message, Principal principal);

    Message showResultOfCurrentQuestion(Message message, Principal principal);

    Message setNextQuestion(Message message, Principal principal);

    Message endVotingAndCollectData(Message message, Principal principal);
}
