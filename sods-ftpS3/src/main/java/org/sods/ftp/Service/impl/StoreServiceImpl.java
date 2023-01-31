package org.sods.ftp.Service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.ftp.Service.StoreService;
import org.sods.ftp.util.S3Util;
import org.springframework.web.multipart.MultipartFile;

public class StoreServiceImpl implements StoreService {
    @Override
    public ResponseResult uploadFile(MultipartFile multipartFile) {
        String message = "";
        Integer code ;
        try {
            S3Util.uploadFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            message = "Your file has been uploaded successfully!";
            code = 200;
        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
            code = 404;
        }

        return new ResponseResult(code,message);
    }
}
