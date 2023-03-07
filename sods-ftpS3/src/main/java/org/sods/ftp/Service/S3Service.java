package org.sods.ftp.Service;

import org.sods.common.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    ResponseResult getAllBucket();
    ResponseResult upload(MultipartFile file);
}
