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

    @PreAuthorize("@ex.hasAuthority('system:resource:pages:get')")
    @GetMapping("/pages")
    public ResponseResult getPageData(@RequestParam(value = "domain",required = false) String domain,
                                      @RequestParam(value = "pathVariable",required = false) String pathVariable,
                                      @RequestParam(value = "language",required = false) String language) {
        return resourceService.getPages(domain,language,pathVariable);
    }
}
