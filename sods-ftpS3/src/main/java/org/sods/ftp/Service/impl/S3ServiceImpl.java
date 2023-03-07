package org.sods.ftp.Service.impl;

import org.apache.http.HttpStatus;
import org.sods.common.domain.ResponseResult;
import org.sods.ftp.Service.S3Service;
import org.sods.ftp.domain.FileRecord;
import org.sods.ftp.mapper.FileRecordMapper;
import org.sods.ftp.util.S3Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    private final String bucketName = "sods-file";
    private final String objectPath = "https://sods-file.s3.ap-northeast-1.amazonaws.com/";

    @Autowired
    private FileRecordMapper fileRecordMapper;

    @Override
    public ResponseResult getAllBucket() {
        List<String> buckets = S3Handler.listBucket();
        return new ResponseResult<>(HttpStatus.SC_OK,"ok",buckets);
    }

    @Override
    public ResponseResult upload(MultipartFile file) {
        //System.out.println(file.getOriginalFilename());

        try {
            S3Handler.uploadFile(bucketName,file);

        }catch (Exception e){
            return new ResponseResult<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Upload failed");
        }

        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(file.getOriginalFilename());
        fileRecord.setUrl(objectPath+file.getOriginalFilename());

        fileRecordMapper.insert(fileRecord);

        return new ResponseResult<>(HttpStatus.SC_OK,"Upload Success");
    }
}
