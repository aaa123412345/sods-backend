package org.sods.resource.Controller;



import org.sods.common.annotation.RedisCacheable;
import org.sods.common.annotation.TimeRecord;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/public")
public class PublicPageResourceController {


    @Autowired
    PageResourceService resourceService;
    //@RedisCacheable(key = "public",expire = "3600")
    @TimeRecord()
    @RedisCacheable(key = "public",expire = "3600")
    @GetMapping("/{language}/{pathVariable}")
    public ResponseResult getPageData(@PathVariable("pathVariable")String pathVariable,
                               @PathVariable("language")String language) {
        return resourceService.get("public",language,pathVariable);
    }

    @PreAuthorize("@ex.hasAuthority('system:cms:create')")
    @PostMapping("/{language}/{pathVariable}")
    public ResponseResult createPageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.post("public",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:cms:update')")
    @PutMapping("/{language}/{pathVariable}")
    public ResponseResult updatePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.put("public",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:cms:delete')")
    @DeleteMapping("/{language}/{pathVariable}")
    public ResponseResult removePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language) {
        return resourceService.delete("public",language,pathVariable);
    }


}
