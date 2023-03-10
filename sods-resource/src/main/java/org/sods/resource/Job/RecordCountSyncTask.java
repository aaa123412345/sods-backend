package org.sods.resource.Job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.Aspect.RESTAspect;
import org.sods.resource.service.CountRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecordCountSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(RecordCountSyncTask.class);
    @Autowired
    private CountRequestService countRequestService;

    //Auto sync request record count to database every 15 minutes
    @Scheduled(fixedDelay = 1000 * 60 * 15)
    public void syncRecordCount(){

        Integer num = countRequestService.countRequestUpdate();
        logger.info("Sync record count to database finished. Total updated: "+num.toString());
    }
}
