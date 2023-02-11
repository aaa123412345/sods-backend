package org.sods.websocket.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonDataResponse {
    public static String getStringWithKey(String key, String string){
        Map<String,String> map = new HashMap<>();
        map.put(key,string);
        return JSONObject.toJSONString(map);
    }

}
