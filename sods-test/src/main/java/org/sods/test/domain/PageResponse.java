package org.sods.test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse {
    private Map<String, Object> page;
    private Map<String, Object> element;



    public PageResponse() {
       page = new HashMap<String,Object>();
       element = new HashMap<String,Object>();
       page.put("useBootstrap",true);
       page.put("useHeader",true) ;
       page.put("title","about");

    }


    public Map<String, Object> getPage() {
        return page;
    }

    public void setPage(Map<String, Object> page) {
        this.page = page;
    }

    public Map<String, Object> getElement() {
        return element;
    }

    public void setElement(Map<String, Object> element) {
        this.element = element;
    }
}
