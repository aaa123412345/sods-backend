package org.sods.ftp.Service.impl;

import org.apache.http.HttpStatus;
import org.sods.common.domain.ResponseResult;
import org.sods.ftp.Service.S3Service;
import org.sods.ftp.util.S3Handler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    private final String bucketName = "sods-file";
    @Override
    public ResponseResult getAllBucket() {
        List<String> buckets = S3Handler.listBucket();
        return new ResponseResult<>(HttpStatus.SC_OK,"ok",buckets);
    }

    @Override
    public ResponseResult upload(MultipartFile file) {
        try {
            S3Handler.uploadFile(bucketName,file);
        }catch (Exception e){
            return new ResponseResult<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Upload failed");
        }

        return new ResponseResult<>(HttpStatus.SC_OK,"Upload Success");
    }
}
