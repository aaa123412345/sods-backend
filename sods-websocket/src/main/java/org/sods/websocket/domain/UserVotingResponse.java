package org.sods.websocket.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVotingResponse {
    private String userID;
    private Map<String,Object> userData;

    public UserVotingResponse(String userID){
        this.userID = userID;
        this.userData = new HashMap<>();

    }

    @JSONField(serialize = false)
    public void addDataToMap(String jsonStr){
        JSONObject userObject = JSON.parseObject(jsonStr);
        String key = userObject.getString("key");
        Object value = userObject.get("value");
        this.userData.put(key,value);
    }

    @JSONField(serialize = false)
    public Boolean fillObjectIfKeyNotExist(String key){
        if(!this.userData.containsKey(key)){
            this.userData.put(key,UserVotingResponse.getEmptyMark());
            return false;
        }

        return true;
    }

    @JSONField(serialize = false)
    public static String getEmptyMark(){
        return "$#empty#";
    }




}
