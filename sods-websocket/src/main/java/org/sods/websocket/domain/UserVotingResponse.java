package org.sods.websocket.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    public void addDataToMap(String jsonStr){
        JSONObject userObject = JSON.parseObject(jsonStr);
        String key = userObject.getString("key");
        String type = userObject.getString("type");

        if(type.equals("String")){
            String value = userObject.getString("value");
            this.userData.put(key,value);
        }else if(type.equals("List")){
            List<String> stringList = (List<String>) userObject.get("value");
            this.userData.put(key,stringList);
        }

    }


}
