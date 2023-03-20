package org.sods.ftp.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.ftp.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/ftp/file")
    public ResponseResult upload(@RequestParam("file") MultipartFile multipart){
        System.out.println("Request Start");
        return s3Service.upload(multipart);
    }

    @DeleteMapping("/ftp/file/{fileName}")
    public ResponseResult delete(@PathVariable("fileName") String fileName){

        return s3Service.delete(fileName);
    }

    //get all files record with filter and the query param can nullable
    @GetMapping("/ftp/files")
    public ResponseResult getFilesRecord(@RequestParam(value = "type",required = false) String type,
                                         @RequestParam(value = "extension",required = false) String extension){
        return s3Service.getFilesRecord(type,extension);
    }



}
