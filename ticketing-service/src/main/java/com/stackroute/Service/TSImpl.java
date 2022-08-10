package com.stackroute.Service;

import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.Exceptions.SeatNotFoundException;
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

    // Service method to change the seat status from "Not Booked" to "Processing"
    @Override
    public Seat getSeat(String eventId,int nid) throws EventNotFoundException {
        log.debug("Inside getSeat");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());
        if(event.getSeats().get(nid).getStatus().equalsIgnoreCase("NOT BOOKED"))
        {
            event.getSeats().get(nid).setStatus("PROCESSING");
            eventRepository.save(event);
            playgroundService.expiry(eventId,nid);
            return event.getSeats().get(nid);
        }
        else
        {
            return event.getSeats().get(nid);
        }
    }

    // Service method to book a ticket with seats
    @Override
//    @Cacheable(value="Seat")
    public Seat bookedTicket(String eventId, int nid) throws EventNotFoundException {
        log.debug("Inside Booked Ticket");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());
        event.setTicketsSold(event.getTicketsSold()+1);
        event.getSeats().get(nid).setStatus("BOOKED");
        eventRepository.save(event);
        return event.getSeats().get(nid);
    }


    // Service method to get Seat Status
    @Override
    public Seat ticketStatus(String eventId, int nid) throws EventNotFoundException {
        log.debug("Getting Seat info");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());
        return event.getSeats().get(nid);
    }

    // Service method to cancel Ticket
    @Override
    public Seat cancelTicket(String eventId, int nid) throws EventNotFoundException {
        log.debug("Cancelling a ticket");
        Event event = eventRepository.findById(eventId).orElseThrow(()->new EventNotFoundException());
        event.getSeats().get(nid).setStatus("NOT BOOKED");
        event.setTicketsSold(event.getTicketsSold()-1);
        eventRepository.save(event);
        return event.getSeats().get(nid);
    }

    //Service method to book tickets with events having no seats

    @Override
    public Event bookTicketforNoSeat(String eventId) throws EventNotFoundException, SeatNotFoundException {
        log.debug("Inside book ticket for seat-UI with no defined layout");
        Event event =  eventRepository.findById(eventId).orElseThrow(()->new EventNotFoundException());
        if(event.getTicketsSold()<event.getTotalSeats()){
            event.setTicketsSold(event.getTicketsSold()+1);
        }
        else{
            throw new SeatNotFoundException();
        }

        eventRepository.save(event);
        return event;
    }

    @Override
    public int bookTicketforforStreaming(String eventId) throws EventNotFoundException {
        Event event =  eventRepository.findById(eventId).orElseThrow(()->new EventNotFoundException());
        event.setTotalSeats(event.getTotalSeats()+1);
        event.setTicketsSold(event.getTicketsSold()+1);
        eventRepository.save(event);
        return event.getTicketsSold();
    }

    // Service method to set the status from "Processing" to "Not Booked" after a scheduled time
    @Override
    public Seat processTicket(String eventId,int nid) throws EventNotFoundException {
        log.debug("Inside process Ticket");

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException());

        if(event.getSeats().get(nid).getStatus().equalsIgnoreCase("PROCESSING"))
        {
            event.getSeats().get(nid).setStatus("NOT BOOKED");
            eventRepository.save(event);
            return event.getSeats().get(nid);
        }
        else
        {
            return event.getSeats().get(nid);
        }
    }

    // Service method to retrieve an Event by Id
    @Override
//    @Cacheable(value="Event", key="#p0")
    public Event getEventById(String eventId) throws EventNotFoundException {
        log.debug("Inside get Event by Id");
        return eventRepository.findById(eventId).orElseThrow(()->new EventNotFoundException());
    }

    //Non cacheable method to get seats
    @Override
    public Event getEventByIdforTix(String eventId) throws EventNotFoundException {
        log.debug("Inside get Event by Id for seat-UI");
        return eventRepository.findById(eventId).orElseThrow(()->new EventNotFoundException());
    }


}

