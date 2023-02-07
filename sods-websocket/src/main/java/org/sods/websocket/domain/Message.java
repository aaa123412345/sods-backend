package org.sods.websocket.domain;

import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private String senderName;
    private String receiverName;
    private String message;
    private Action action;
    private String data;
    private Status status;
}
