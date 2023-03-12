package org.sods.resource.Job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.resource.service.RecordRequestTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeRecordSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(TimeRecordSyncTask.class);
    @Autowired
    private RecordRequestTimeService recordRequestTimeService;

    //Auto sync request record count to database every 15 minutes
    @Scheduled(fixedDelay = 1000 * 60 * 15)
    public void syncRecordCount(){
          Integer num = recordRequestTimeService.recordRequestTimeUpdate();
          logger.info("Sync time usage record to database finished. Total updated: "+num.toString());
    }
}
