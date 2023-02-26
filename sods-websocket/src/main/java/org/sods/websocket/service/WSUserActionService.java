package org.sods.websocket.service;

import org.sods.websocket.domain.Message;

import java.security.Principal;

public interface WSUserActionService {

    Message submitAction(Message message, Principal principal);



}
