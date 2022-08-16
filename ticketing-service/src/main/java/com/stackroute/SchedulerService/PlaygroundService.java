package com.stackroute.SchedulerService;

import com.stackroute.Jobs.Expiry;
import com.stackroute.model.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaygroundService {
    private final SchedulerService scheduler;

    @Autowired
    public PlaygroundService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void expiry(String id, int nid) {
        final TimerInfo info = new TimerInfo();
        info.setTotalFireCount(1);

        info.setInitialOffsetMs(174000);
        info.setCallbackData(id);
        info.setSeatId(nid);

        scheduler.schedule(Expiry.class, info);
    }
}
