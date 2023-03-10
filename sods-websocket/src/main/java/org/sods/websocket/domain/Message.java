package org.sods.websocket.domain;

import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private String senderName;  //Participant
    private String receiverName; //Target Voting
    private String data; //Json String
    private Action action;
    private Status status;

    public static Message getServerMessage(String rawPasscode,Action action,Status status,String data){
        Message message = new Message("Server","Group:"+rawPasscode,data,action,status);
        return message;
    }

    public static Message getSynchronizationMessage(String rawPasscode,String data){
        Message message = new Message("Server",
                "Group:"+rawPasscode,data,Action.SYNCHRONIZATION,Status.COMMAND);
        return message;
    }
}
