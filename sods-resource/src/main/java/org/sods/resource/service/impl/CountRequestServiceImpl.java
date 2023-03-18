package org.sods.resource.service.impl;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.utils.RedisCache;
import org.sods.resource.domain.RequestCount;
import org.sods.resource.domain.RequestTimeRecord;
import org.sods.resource.mapper.RequestCountMapper;
import org.sods.resource.service.CountRequestService;
import org.sods.resource.service.RecordRequestTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountRequestServiceImpl implements CountRequestService {

    private static final Logger logger = LoggerFactory.getLogger(CountRequestService.class);
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private RequestCountMapper requestCountMapper;

    @Override
    public Integer countRequestUpdate() {
        //Get all keys with prefix "COUNT:"
        List<String> keyList = new ArrayList<String>( redisCache.keys("COUNT:*"));
        List<RequestCount> requestCountList = new ArrayList<RequestCount>();
        keyList.forEach((e)->{
            Object value = redisCache.getCacheObject(e);
            if(value instanceof Integer){
                RequestCount requestCount = new RequestCount();
                requestCount.setCount((Integer) value);
                String substring = e.substring(e.indexOf(":") + 1);
                requestCount.setRequestItem(substring);
                requestCountList.add(requestCount);
            }
        });

        if(requestCountList.size()==0){
            return 0;
        }
        //Split data with the size 1000 and Batch insert into database
        splittedButchInsert(requestCountList, 1000);

        //Delete all keys with prefix "COUNT:"
        keyList.forEach((e)->{
            redisCache.deleteObject(e);
        });

        return keyList.size();
    }

    public boolean splittedButchInsert(List<RequestCount> requestCounts, int batchSize){
        int size = requestCounts.size();
        int batchCount = (size + batchSize - 1) / batchSize;
        for (int i = 0; i < batchCount; i++) {
            int fromIndex = i * batchSize;
            int toIndex = Math.min(size, (i + 1) * batchSize);
            List<RequestCount> subList = requestCounts.subList(fromIndex, toIndex);
            requestCountMapper.batchInsert(subList);
        }
        logger.info("Splitted batch insert finished:"+size+" records");
        return true;
    }


}
