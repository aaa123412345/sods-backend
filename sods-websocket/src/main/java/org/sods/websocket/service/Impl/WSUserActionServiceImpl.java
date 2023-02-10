package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.websocket.domain.Action;
import org.sods.websocket.domain.Message;
import org.sods.websocket.domain.Status;
import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.WSUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
public class WSUserActionServiceImpl implements WSUserActionService {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public Message addAction(Message message, Principal principal) {

        String rawPasscode = message.getReceiverName();
        String passcode = "Voting:" + rawPasscode;
        VotingState votingState = redisCache.getCacheObject(passcode);
        if(!Objects.isNull(votingState)) {
            votingState.setClickCount(votingState.getClickCount() + 1);
            redisCache.setCacheObject(passcode, votingState);

            message.setData(JSONObject.toJSONString(votingState));
            message.setStatus(Status.COMMAND);
            message.setAction(Action.SYNCHRONIZATION);
            message.setSenderName("Server");
            message.setReceiverName("Group:"+rawPasscode);
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private", message);

        }
        return message;
    }

    @Override
    public Message minusAction(Message message, Principal principal) {
        String rawPasscode = message.getReceiverName();
        String passcode = "Voting:" + rawPasscode;
        VotingState votingState = redisCache.getCacheObject(passcode);
        if(!Objects.isNull(votingState)) {
            votingState.setClickCount(votingState.getClickCount() - 1);
            redisCache.setCacheObject(passcode, votingState);

            message.setData(JSONObject.toJSONString(votingState));
            message.setStatus(Status.COMMAND);
            message.setAction(Action.SYNCHRONIZATION);
            message.setSenderName("Server");
            message.setReceiverName("Group:"+rawPasscode);
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private", message);
        }
        return message;
    }


}
