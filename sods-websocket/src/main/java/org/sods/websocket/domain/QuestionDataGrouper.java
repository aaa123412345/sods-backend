package org.sods.websocket.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class QuestionDataGrouper {
    private ResultRenderMethod resultRenderMethod;

    private String questionType;
    private Map<String,Integer> map;

    //WordCloud => (string,int)
    //PieChart => (string,int)


    public QuestionDataGrouper(String questionType){
       switch (questionType){
           case "stext": resultRenderMethod = ResultRenderMethod.WORDCLOUD;break;
           case "sselect":
           case "schecker":
           case "sradio": resultRenderMethod = ResultRenderMethod.PIECHART;break;
           case "srange": resultRenderMethod = ResultRenderMethod.LINECHART;break;
       }
       this.questionType = questionType;
       map = new HashMap<>();
    }

    @JSONField(serialize = false)
    public void addStringToMap(String s){
        if(map.containsKey(s)){
            map.put(s,map.get(s)+1);
        }else {
            map.put(s,1);
        }
    }
    @JSONField(serialize = false)
    public void collectDataAndPutIntoMap(Object value){
        if(value instanceof String){
            addStringToMap((String) value);
        } else if (value instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) value;

            jsonArray.forEach((e)->{
                String tmp = (String) e;
                addStringToMap(tmp);
            });
        } else if (value instanceof Integer) {
            Integer i = (Integer) value;
            addStringToMap(i.toString());
        }
    }

    @JSONField(serialize = false)
    public void resetRenderMethodAccordingToDataSize(){

        if(map.size()<10){
            this.resultRenderMethod = ResultRenderMethod.BARCHART;
        }

    }
}
