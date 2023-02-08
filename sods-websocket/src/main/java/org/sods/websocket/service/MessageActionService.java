package org.sods.websocket.service;

import org.sods.websocket.domain.Message;

import java.security.Principal;

public interface MessageActionService {
    Message addAction(Message message, Principal principal);
    Message minusAction(Message message, Principal principal);
    Message clearAction(Message message, Principal principal);
}
