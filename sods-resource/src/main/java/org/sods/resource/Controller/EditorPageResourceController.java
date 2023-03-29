package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class EditorPageResourceController {
    @Autowired
    private PageResourceService resourceService;

    @PreAuthorize("@ex.hasAuthority('system:cms:get')")
    @GetMapping("/pages")
    public ResponseResult getPageData(@RequestParam(value = "domain",required = false) String domain,
                                      @RequestParam(value = "pathVariable",required = false) String pathVariable,
                                      @RequestParam(value = "language",required = false) String language,
                                      @RequestParam(value = "editable",required = false) Boolean editable) {
        return resourceService.getPages(domain,language,pathVariable,editable);
    }

    @PreAuthorize("@ex.hasAuthority('system:cms:put')")
    @PutMapping("/pages")
    public ResponseResult putPageData(@RequestParam(value = "domain",required = false) String domain,
                                      @RequestParam(value = "pathVariable",required = false) String pathVariable,
                                      @RequestParam(value = "language",required = false) String language,
                                      @RequestBody String payload) {
        return resourceService.forceUpdate(domain,language,pathVariable,payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:cms:delete')")
    @DeleteMapping("/pages")
    public ResponseResult removePageData(@RequestParam(value = "domain",required = false) String domain,
                                      @RequestParam(value = "pathVariable",required = false) String pathVariable,
                                      @RequestParam(value = "language",required = false) String language) {
        return resourceService.delete(domain,language,pathVariable);
    }
}
