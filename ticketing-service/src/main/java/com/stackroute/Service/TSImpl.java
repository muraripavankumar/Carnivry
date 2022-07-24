package com.stackroute.Service;

import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.SchedulerService.PlaygroundService;
import com.stackroute.model.Event;
import com.stackroute.Repository.EventRepository;
import com.stackroute.model.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TSImpl implements TicketingService{

    private final PlaygroundService playgroundService;


    private final EventRepository eventRepository;

    @Autowired
    public TSImpl(PlaygroundService playgroundService, EventRepository eventRepository) {
        log.info("constructing TSImpl");
        this.playgroundService = playgroundService;
        this.eventRepository = eventRepository;
    }

    //
    @Override
    public Seat getSeat(String eventId,int nid) throws EventNotFoundException {
        log.debug("Inside getSeat");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());
        if(event.getSeats().get(nid).getStatus().equalsIgnoreCase("Not Booked"))
        {
            event.getSeats().get(nid).setStatus("Processing");
            eventRepository.save(event);
            playgroundService.expiry(eventId,nid);

        }
        else
        {
            return event.getSeats().get(nid);
        }

        return event.getSeats().get(nid);
    }


    @Override
    @Cacheable(value="Seat")
    public Seat bookedTicket(String eventId, int nid) throws EventNotFoundException {
        log.debug("Inside Booked Ticket");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());
        event.getSeats().get(nid).setStatus("Booked");
        eventRepository.save(event);
        return event.getSeats().get(nid);
    }

    @Override

    public Seat ticketStatus(String eventId, int nid) throws EventNotFoundException {
        log.debug("Getting Seat info");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());
        return event.getSeats().get(nid);
    }


    @Override
    public Seat processTicket(String eventId,int nid) throws EventNotFoundException {
        log.debug("Inside process Ticket");

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());

        if(event.getSeats().get(nid).getStatus().equalsIgnoreCase("Processing"))
        {
            event.getSeats().get(nid).setStatus("Not Booked");
            eventRepository.save(event);
        }
        else
        {
            return event.getSeats().get(nid);
        }
        return event.getSeats().get(nid);
    }

    @Override
    @Cacheable(value="Event", key="#p0")
    public Event getEventById(String eventId) throws EventNotFoundException {
        log.debug("Inside get Event by Id");
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }



}

