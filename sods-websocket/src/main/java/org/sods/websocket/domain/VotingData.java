package org.sods.websocket.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VotingData {
    private String ActiveSurveyID;
    private String SurveyID;
    private String SurveyFormat;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
