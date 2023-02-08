package org.sods.websocket.domain;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotingState {
    private String Passcode; //Room name
    private List<String> Participant;
    private Integer ClickCount;

}
