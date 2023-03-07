package org.sods.ftp.Service;

import org.sods.common.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    ResponseResult getAllBucket();
    ResponseResult upload(MultipartFile file);

    ResponseResult delete(String fileName);

    ResponseResult getFilesRecord(String type, String extension);
}
