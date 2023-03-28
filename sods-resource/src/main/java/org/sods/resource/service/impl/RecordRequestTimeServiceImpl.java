package org.sods.resource.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.domain.RedisUrlTimeRecord;
import org.sods.common.utils.RedisCache;

import org.sods.resource.domain.RequestTimeRecord;
import org.sods.resource.mapper.RequestTimeRecordMapper;
import org.sods.resource.service.RecordRequestTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordRequestTimeServiceImpl implements RecordRequestTimeService {

    private static final Logger logger = LoggerFactory.getLogger(RecordRequestTimeService.class);
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private RequestTimeRecordMapper requestTimeRecordMapper;

    @Override
    public Integer recordRequestTimeUpdate() {
        //Get all keys with prefix "TIMERECORD:RECORD:"
        List<String> keyList = new ArrayList<String>( redisCache.keys("TIMERECORD:RECORD:*"));
        List<RequestTimeRecord> requestTimeRecords = new ArrayList<RequestTimeRecord>();


        keyList.forEach((e)->{
            Object value = redisCache.getCacheObject(e);
            if(value instanceof RedisUrlTimeRecord){
                RedisUrlTimeRecord redisUrlTimeRecord = (RedisUrlTimeRecord) value;
                RequestTimeRecord requestTimeRecord = new RequestTimeRecord();
                requestTimeRecord.setUrl(redisUrlTimeRecord.getUrl());
                String tmpUser = redisUrlTimeRecord.getUser();
                if(tmpUser.equals("Anonymous")){
                    requestTimeRecord.setUserId(-999L);
                }else{
                    try{
                        requestTimeRecord.setUserId(Long.parseLong(tmpUser));
                    } catch (NumberFormatException ex){
                        logger.error("User id is not a number: " + tmpUser);
                        requestTimeRecord.setUserId(-999L);
                    }

                }
                requestTimeRecord.setTimeInSecond(redisUrlTimeRecord.getTimeInSecond());
                requestTimeRecords.add(requestTimeRecord);
            }
        });

        System.out.println(requestTimeRecords);

        if(requestTimeRecords.size()==0){
            return 0;
        }

        //split the list into 1000 size and insert into database
        splitButchInsert(requestTimeRecords,1000);



        //Delete all keys with prefix "TIMERECORD:RECORD:"
        keyList.forEach((e)->{
            redisCache.deleteObject(e);
        });



        return keyList.size();
    }

    public boolean splitButchInsert(List<RequestTimeRecord> requestTimeRecords, int batchSize){
        int size = requestTimeRecords.size();
        int batchCount = (size + batchSize - 1) / batchSize;
        for (int i = 0; i < batchCount; i++) {
            int fromIndex = i * batchSize;
            int toIndex = Math.min(size, (i + 1) * batchSize);
            List<RequestTimeRecord> subList = requestTimeRecords.subList(fromIndex, toIndex);
            requestTimeRecordMapper.batchInsert(subList);
        }
        logger.info("Splitted batch insert finished:"+size+" records");
        return true;
    }


}
