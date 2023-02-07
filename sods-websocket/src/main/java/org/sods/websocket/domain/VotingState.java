package org.sods.websocket.domain;

import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotingState {
    private String Passcode; //Room name
    private Integer Participant;
    private Integer ClickCount;

}
