package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/server")
public class ServerPageResourceController {


    @Autowired
    private PageResourceService resourceService;

    @PreAuthorize("@ex.hasAuthority('system:resource:server:get')")
    @GetMapping("/{language}/{pathVariable}")
    public ResponseResult getPageData(@PathVariable("pathVariable")String pathVariable,
                               @PathVariable("language")String language) {
        return resourceService.get("server",language,pathVariable);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:server:create')")
    @PostMapping("/{language}/{pathVariable}")
    public ResponseResult createPageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.post("server",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:server:update')")
    @PutMapping("/{language}/{pathVariable}")
    public ResponseResult updatePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language,
                                         @RequestBody String payload) {
        return resourceService.put("server",language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:resource:server:delete')")
    @DeleteMapping("/{language}/{pathVariable}")
    public ResponseResult removePageData(@PathVariable("pathVariable")String pathVariable,
                                      @PathVariable("language")String language) {
        return resourceService.delete("server",language,pathVariable);
    }


}
