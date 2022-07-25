package com.stackroute.Util;

import com.stackroute.Jobs.Expiry;
import com.stackroute.model.TimerInfo;
import org.quartz.*;

import java.util.Date;

public final class TimerUtils {
    private TimerUtils() {
    }

    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(String.valueOf(Math.random()))
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule();

        if (info.isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1);
        }

        return
                TriggerBuilder
                .newTrigger()
                .withIdentity(String.valueOf(Math.random()))
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
                .build();
    }
}
