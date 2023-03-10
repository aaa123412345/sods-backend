package org.sods.resource.Controller;

import org.sods.common.annotation.RedisCacheable;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/pageimportant")
public class ImportantPageResourceController {


    @Autowired
    private PageResourceService resourceService;
    @RedisCacheable(key = "page-important",expire = "3600")
    @GetMapping("/{language}/{pathVariable}")
    public ResponseResult getPageData(@PathVariable("pathVariable")String pathVariable,
                               @PathVariable("language")String language) {
        return resourceService.get("pageimportant",language,pathVariable);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:basicWeb:create')")
    @PostMapping("/{language}/{pathVariable}")
    public ResponseResult createPageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.post("pageimportant",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:basicWeb:update')")
    @PutMapping("/{language}/{pathVariable}")
    public ResponseResult updatePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        resourceService.makeBackup("pageimportant",language,pathVariable);
        return resourceService.put("pageimportant",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:basicWeb:delete')")
    @DeleteMapping("/{language}/{pathVariable}")
    public ResponseResult removePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language) {
        return resourceService.delete("pageimportant",language,pathVariable);
    }


}
