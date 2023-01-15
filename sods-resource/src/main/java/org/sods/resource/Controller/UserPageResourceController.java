package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/user")
public class UserPageResourceController {


    @Autowired
    private PageResourceService resourceService;
    @GetMapping("/{language}/{pathVariable}")
    public ResponseResult getPageData(@PathVariable("pathVariable")String pathVariable,
                               @PathVariable("language")String language) {
        return resourceService.get("user",language,pathVariable);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:public:create')")
    @PostMapping("/{language}/{pathVariable}")
    public ResponseResult createPageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.post("user",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:public:update')")
    @PutMapping("/{language}/{pathVariable}")
    public ResponseResult updatePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.put("user",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:public:delete')")
    @DeleteMapping("/{language}/{pathVariable}")
    public ResponseResult removePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language) {
        return resourceService.delete("user",language,pathVariable);
    }


}
