package org.sods.resource.Controller;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.PageData;
import org.sods.resource.mapper.PageDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicResourceController {
    @Autowired
    private PageDataMapper pageMapper;
    @Autowired
    @GetMapping("/bye")
    @PreAuthorize("hasAuthority('system:123:list')")
    public String bye(){
        return  "bye";
    }
    @GetMapping("/public/about")
    public ResponseResult about(){
        PageData s = pageMapper.selectById("about");

        return new ResponseResult(200,"hi", JSONObject.parseObject(s.getPageData()));
    }
}
