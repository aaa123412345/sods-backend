package org.sods.websocket.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotingState {
    private String activeSurveyID;
    private String surveyID;
    private String surveyFormat;
    private String Passcode; //Room name
    private List<String> participantJoin;
    private List<String> participantSubmit;
    private Integer currentQuestion;
    private ClientRenderMethod clientRenderMethod;
    private LocalDateTime startTime;
    private LocalDateTime endTime;



    public VotingState(String Passcode){
        this.setActiveSurveyID("Test");
        this.setSurveyID("Test");
        this.setSurveyFormat("Test");
        this.setStartTime(LocalDateTime.now());
        this.setPasscode(Passcode);
        this.setParticipantJoin(new ArrayList<>());
        this.setParticipantSubmit(new ArrayList<>());
        this.setCurrentQuestion(1);
        this.setClientRenderMethod(ClientRenderMethod.VOTING);
    }
    public Boolean addParticipantJoinIfNotExist(String userName){
        if(!this.participantJoin.contains(userName)){
            this.participantJoin.add(userName);

            return true;
        }
        return false;
    }

    public Boolean removeParticipantJoinIfExist(String userName){
        if(this.participantJoin.contains(userName)){
            this.participantJoin.remove(userName);
            return true;
        }
        return false;
    }

    public Boolean addParticipantSubmitIfNotExist(String userName){
        if(!this.participantSubmit.contains(userName)){
            this.participantSubmit.add(userName);

            return true;
        }
        return false;
    }

    public Boolean removeParticipantSubmitIfExist(String userName){
        if(this.participantSubmit.contains(userName)){
            this.participantSubmit.remove(userName);
            return true;
        }
        return false;
    }

    public String getJSONResponse(){
        Map<String,Object> map = new HashMap<>();
        map.put("passcode",this.Passcode);
        map.put("participantJoin",this.participantJoin.size());
        map.put("participantSubmit",this.participantSubmit.size());
        map.put("currentPartID",this.currentQuestion.toString());
        map.put("clientRenderMethod",this.clientRenderMethod);
        return JSONObject.toJSONString(map);
    }

    public List<String> getUserResponseRedisKeyList(){
        List<String> list = new ArrayList<>();
        participantJoin.forEach((e)->{
            list.add(VotingState.getUserResponseRedisKeyString(Passcode,e));
        });
        return list;
    }

    public static String getUserResponseRedisKeyString(String passcode,String user){
        return "Voting:"+passcode+":"+user;
    }

    public static String getGlobalVotingDataRedisKeyString(String passcode){
        return "Voting:"+passcode;
    }
}
