package org.sods.websocket.service.Impl;

import org.sods.websocket.domain.Message;
import org.sods.websocket.service.MessageActionService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MessageActionServiceImpl implements MessageActionService {
    @Override
    public Message addAction(Message message, Principal principal) {
        return null;
    }

    @Override
    public Message minusAction(Message message, Principal principal) {
        return null;
    }

    @Override
    public Message clearAction(Message message, Principal principal) {
        return null;
    }
}
