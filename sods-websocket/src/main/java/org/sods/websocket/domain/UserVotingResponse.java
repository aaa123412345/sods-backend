package org.sods.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserVotingResponse {
    private String userName;
    private Map<String,Object> userData;

    public UserVotingResponse(String userName){
        this.userName = userName;
        this.userData = new HashMap<>();
    }

    public void addDataToMap(String jsonStr){


    }


}
