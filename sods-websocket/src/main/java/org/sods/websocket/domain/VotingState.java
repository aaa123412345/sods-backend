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

        this.setSurveyID("Test");
        this.setSurveyFormat("Test");
        this.setStartTime(LocalDateTime.now());
        this.setPasscode(Passcode);
        this.setParticipantJoin(new ArrayList<>());
        this.setParticipantSubmit(new ArrayList<>());
        this.setCurrentQuestion(0);
        this.setClientRenderMethod(ClientRenderMethod.VOTING);
    }

    @JSONField(serialize = false)
    public Boolean addParticipantJoinIfNotExist(String userName){
        if(!this.participantJoin.contains(userName)){
            this.participantJoin.add(userName);

            return true;
        }
        return false;
    }

    @JSONField(serialize = false)
    public Boolean removeParticipantJoinIfExist(String userName){
        if(this.participantJoin.contains(userName)){
            this.participantJoin.remove(userName);
            return true;
        }
        return false;
    }

    @JSONField(serialize = false)
    public Boolean addParticipantSubmitIfNotExist(String userName){
        if(!this.participantSubmit.contains(userName)){
            this.participantSubmit.add(userName);

            return true;
        }
        return false;
    }

    @JSONField(serialize = false)
    public Boolean removeParticipantSubmitIfExist(String userName){
        if(this.participantSubmit.contains(userName)){
            this.participantSubmit.remove(userName);
            return true;
        }
        return false;
    }

    @JSONField(serialize = false)
    public String getJSONResponse(){
        Map<String,Object> map = new HashMap<>();
        map.put("passcode",this.Passcode);
        map.put("surveyID",this.surveyID);
        map.put("participantJoin",this.participantJoin.size());
        map.put("participantSubmit",this.participantSubmit.size());
        map.put("currentQuestion",this.currentQuestion.toString());
        map.put("clientRenderMethod",this.clientRenderMethod);
        return JSONObject.toJSONString(map);
    }



    @JSONField(serialize = false)
    public String getJSONResponseWithRenderData(String jsonString){
        Map<String,Object> map = new HashMap<>();
        map.put("passcode",this.Passcode);
        map.put("participantJoin",this.participantJoin.size());
        map.put("participantSubmit",this.participantSubmit.size());
        map.put("currentQuestion",this.currentQuestion.toString());
        map.put("clientRenderMethod",this.clientRenderMethod);
        map.put("renderData",jsonString);
        return JSONObject.toJSONString(map);
    }
    @JSONField(serialize = false)
    public List<String> getUserResponseRedisKeyList(){
        List<String> list = new ArrayList<>();
        participantJoin.forEach((e)->{
            list.add(VotingState.getUserResponseRedisKeyString(Passcode,e));
        });
        return list;
    }
    @JSONField(serialize = false)
    public String getCurrentQuestionFormat(){
        JSONObject formatObject = JSONObject.parseObject(surveyFormat);

        //Find Part Key
        List<String> stringList = (List<String>) formatObject.getJSONObject("info").get("partKey");
        String partKey = stringList.get(0);

        //Get question set
        List<Object> objectList = (List<Object>) formatObject.getJSONObject("questionset").get(partKey);


        return JSONObject.toJSONString(objectList.get(currentQuestion));
    }

    @JSONField(serialize = false)
    public static String getUserResponseRedisKeyString(String passcode,String user){
        return "Voting:"+passcode+":"+user;
    }

    @JSONField(serialize = false)
    public static String getGlobalVotingDataRedisKeyString(String passcode){
        return "Voting:"+passcode;
    }
}
