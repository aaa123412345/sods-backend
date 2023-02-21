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
    private Integer maxQuestion;
    private ClientRenderMethod clientRenderMethod;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String renderData;




    public VotingState(String Passcode,String surveyID,String surveyFormat){

        this.setSurveyID(surveyID);
        this.setSurveyFormat(surveyFormat);
        this.setStartTime(LocalDateTime.now());
        this.maxQuestion = this.countMaxQuestion();
        this.setPasscode(Passcode);
        this.setParticipantJoin(new ArrayList<>());
        this.setParticipantSubmit(new ArrayList<>());
        this.setCurrentQuestion(1);
        this.setClientRenderMethod(ClientRenderMethod.VOTING);
        this.setRenderData(this.getCurrentQuestionFormat());
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
        map.put("passcode",Passcode);
        map.put("surveyID",surveyID);

        map.put("participantJoin",participantJoin.size());
        map.put("participantSubmit",participantSubmit.size());
        map.put("currentQuestion",currentQuestion.toString());
        map.put("currentQuestionMsg",this.getCurrentQuestionMsg());
        map.put("maxQuestion",maxQuestion);
        map.put("clientRenderMethod",clientRenderMethod);
        return JSONObject.toJSONString(map);
    }

    @JSONField(serialize = false)
    public Map<String, Object> getJSONMapResponse(){
        Map<String,Object> map = new HashMap<>();
        map.put("passcode",Passcode);
        map.put("surveyID",surveyID);
        map.put("startTime",startTime);
        map.put("participantJoin",participantJoin.size());
        map.put("participantSubmit",participantSubmit.size());
        map.put("currentQuestion",currentQuestion.toString());
        map.put("currentQuestionMsg",this.getCurrentQuestionMsg());
        map.put("maxQuestion",maxQuestion);
        map.put("clientRenderMethod",clientRenderMethod);
        return map;
    }

    @JSONField(serialize = false)
    public String getCurrentQuestionType(){
        JSONObject formatObject = JSONObject.parseObject(surveyFormat);
        //Find Part Key
        List<String> stringList = (List<String>) formatObject.getJSONObject("info").get("partKey");
        String partKey = stringList.get(0);
        //Get question set
        List<JSONObject> objectList = (List<JSONObject>) formatObject.getJSONObject("questionset").get(partKey);

        return objectList.get(currentQuestion-1).getString("type");
    }
    @JSONField(serialize = false)
    public String getJSONResponseWithRenderData(){
        Map<String,Object> map = new HashMap<>();
        map.put("passcode",Passcode);
        map.put("participantJoin",participantJoin.size());
        map.put("participantSubmit",participantSubmit.size());
        map.put("currentQuestion",currentQuestion.toString());
        map.put("currentQuestionMsg",this.getCurrentQuestionMsg());
        map.put("maxQuestion",maxQuestion);
        map.put("clientRenderMethod",clientRenderMethod);
        map.put("renderData",renderData);
        return JSONObject.toJSONString(map);
    }

    @JSONField(serialize = false)
    public Boolean checkAndSetToNextQuestion(){
        if(currentQuestion<maxQuestion){
            this.currentQuestion=currentQuestion+1;
            this.clientRenderMethod=ClientRenderMethod.VOTING;
            this.renderData=getCurrentQuestionFormat();
            this.participantSubmit=new ArrayList<>();
            return true;
        }else{
            return false;
        }
    }
    @JSONField(serialize = false)
    public Integer countMaxQuestion(){
        JSONObject formatObject = JSONObject.parseObject(surveyFormat);

        //Find Part Key
        List<String> stringList = (List<String>) formatObject.getJSONObject("info").get("partKey");
        String partKey = stringList.get(0);

        //Get question set
        List<Object> objectList = (List<Object>) formatObject.getJSONObject("questionset").get(partKey);

        return objectList.size();
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


        return JSONObject.toJSONString(objectList.get(currentQuestion-1));
    }
    @JSONField(serialize = false)
    public String getCurrentQuestionMsg(){
        JSONObject formatObject = JSONObject.parseObject(surveyFormat);

        //Find Part Key
        List<String> stringList = (List<String>) formatObject.getJSONObject("info").get("partKey");
        String partKey = stringList.get(0);

        //Get question set
        List<JSONObject> objectList = (List<JSONObject>) formatObject.getJSONObject("questionset").get(partKey);


        return objectList.get(currentQuestion-1).getString("msg");
    }
    @JSONField(serialize = false)
    public String getCurrentSurveyFormatPartKey(){
        JSONObject formatObject = JSONObject.parseObject(surveyFormat);
        //Find Part Key
        List<String> stringList = (List<String>) formatObject.getJSONObject("info").get("partKey");
        return stringList.get(0);
    }

    @JSONField(serialize = false)
    public static String getUserResponseRedisKeyString(String passcode,String user){
        return "VotingClient:"+passcode+":"+user;
    }

    @JSONField(serialize = false)
    public static String getGlobalVotingDataRedisKeyString(String passcode){
        return "Voting:"+passcode;
    }
}
