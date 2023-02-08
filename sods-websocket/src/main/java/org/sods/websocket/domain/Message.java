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
}
