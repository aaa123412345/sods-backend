package org.sods.ftp.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.ftp.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FTPServerController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/ftp/upload")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile multipart){
        return storeService.uploadFile(multipart);
    }
}
