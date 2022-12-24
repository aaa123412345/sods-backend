package org.sods.test.Controller;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.domain.ResponseResult;
import org.sods.test.domain.Page;
import org.sods.test.domain.PageResponse;
import org.sods.test.mapper.PageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    @GetMapping("/bye")
    @PreAuthorize("hasAuthority('system:123:list')")
    public String bye(){
        return  "bye";
    }
    @GetMapping("/public/about")
    public ResponseResult about(){
        Page s = pageMapper.selectById("about");

        return new ResponseResult(200,"hi",JSONObject.parseObject(s.getData()));
    }

}
