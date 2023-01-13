package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/public")
public class PublicPageResourceController {
    /*
    @Autowired
    private PageRouterMapper pageRouterMapper;
    @Autowired
    @GetMapping("/bye")
    @PreAuthorize("hasAuthority('system:123:list')")
    public String bye(){
        return  "bye";
    }
    @GetMapping("/public/about")
    public ResponseResult about(){
        String s = pageRouterMapper.selectPageDataByURLAndLan("public/about","eng");

        return new ResponseResult(200,"hi", JSONObject.parseObject(s));
    }

    @GetMapping("/public/{language}/{pathVariable}")
    public ResponseResult test(@PathVariable("pathVariable")String pathVariable,
                               @PathVariable("language")String language){

        String s = pageRouterMapper.selectPageDataByURLAndLan("public/"+pathVariable,language);
        if(StringUtils.hasText(s)) {
            return new ResponseResult(200, "ready", JSONObject.parseObject(s));
        }else{
            return new ResponseResult(404, "Page not found");
        }
    }*/

    @Autowired
    PageResourceService resourceService;
    @GetMapping("/{language}/{pathVariable}")
    public ResponseResult test(@PathVariable("pathVariable")String pathVariable,
                               @PathVariable("language")String language) {
        return resourceService.get("public",language,pathVariable);
    }


}
