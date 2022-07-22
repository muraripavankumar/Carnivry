package com.stackroute.Controller;


import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.Service.TicketingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketController {

    private TicketingService ticketingService;

    @Autowired
    public TicketController(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

   // http://localhost:5300/ticket/{eventId} get
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventByEventId(@PathVariable String eventId) throws Exception {
        try {
            return new ResponseEntity<>(ticketingService.getEventById(eventId), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            log.error("Exception occured in TicketController->getEventByEventId");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (Exception ex){
            log.error("Exception occured in TicketController->getEventByEventId");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }
    // http://localhost:5300/ticket/get/{eventId}/{nid} get
    @GetMapping("/get/{eventId}/{nid}")
    public ResponseEntity<?> getTicket(@PathVariable String eventId,@PathVariable int nid) throws EventNotFoundException {
    try{
        return new ResponseEntity<>(ticketingService.getSeat(eventId,nid), HttpStatus.OK);
    }
        catch ( EventNotFoundException s)
        {
            log.error("Exception occured in TicketController->getTicket");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException)
        {
            log.error("Exception occured in TicketController->getTicket");
            return new ResponseEntity<>("The particular seat is not available",HttpStatus.CONFLICT);
        }
    catch (Exception ex)
    {
        log.error("Exception occured in TicketController->getTicket");
        return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
    }

    }

    // http://localhost:5300/ticket/booked/{eventId}/{nid} get
    @GetMapping("/booked/{eventId}/{nid}")
    public ResponseEntity<?> getBookedTicket(@PathVariable String eventId,@PathVariable int nid) throws Exception {
        try {
            return new ResponseEntity<>(ticketingService.bookedTicket(eventId, nid), HttpStatus.OK);
        }
        catch ( EventNotFoundException s)
        {
            log.error("Exception occured in TicketController->getBookedTicket");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException)
        {
            log.error("Exception occured in TicketController->getBookedTicket");
            return new ResponseEntity<>("The particular seat is not available",HttpStatus.CONFLICT);
        }
        catch (Exception ex)
        {
            log.error("Exception occured in TicketController->getBookedTicket");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }
}
