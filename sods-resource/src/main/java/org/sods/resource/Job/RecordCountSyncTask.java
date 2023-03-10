package org.sods.resource.Job;

import org.sods.resource.service.CountRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecordCountSyncTask {
    @Autowired
    private CountRequestService countRequestService;

    //Auto sync request record count to database every 15 minutes
    @Scheduled(fixedDelay = 1000 * 60 * 15)
    public void syncRecordCount(){
        countRequestService.countRequestUpdate();
    }
}
