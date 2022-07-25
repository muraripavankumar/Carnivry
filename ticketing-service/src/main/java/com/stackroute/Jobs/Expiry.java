package com.stackroute.Jobs;


import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.Service.TicketingService;
import com.stackroute.model.TimerInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Expiry implements Job {


    private TicketingService ticketingService;

    @Autowired
    public Expiry(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }




    private static final Logger LOG= LoggerFactory.getLogger(Expiry.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo info = (TimerInfo) jobDataMap.get(Expiry.class.getSimpleName());
        try {
            ticketingService.processTicket(info.getCallbackData(), info.getSeatId());
        } catch (EventNotFoundException e) {
            e.printStackTrace();
        }
        LOG.info("Scheduler started for eventId "+info.getCallbackData() + "/ SeatId " + info.getSeatId());
    }
}