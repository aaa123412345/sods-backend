package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.sods.resource.domain.BoothRecord;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface BoothRecordService {

    ResponseResult createRecord(BoothRecord boothRecord);
    ResponseResult getAllRecords(Long userId);
    ResponseResult updateRecordByUserIdAndBoothId(Long userId, Integer boothId);

}
