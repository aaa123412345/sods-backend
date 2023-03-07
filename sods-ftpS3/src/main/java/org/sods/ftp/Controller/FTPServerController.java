package org.sods.ftp.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.ftp.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FTPServerController {

    //@Autowired
    //private StoreService storeService;

    @Autowired
    private S3Service s3Service;
    /*
    @PostMapping("/ftp/upload")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile multipart){
        return storeService.uploadFile(multipart);
    }*/

    @GetMapping("/ftp/bucket")
    public ResponseResult listBucket(){
        return s3Service.getAllBucket();
    }

    @PostMapping("/ftp/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile multipart){
        System.out.println("Request Start");
        return s3Service.upload(multipart);
    }
}
