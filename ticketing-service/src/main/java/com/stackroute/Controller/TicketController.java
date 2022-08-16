package com.stackroute.Controller;


import com.stackroute.Exceptions.EventNotFoundException;
import com.stackroute.Exceptions.SeatNotFoundException;
import com.stackroute.Service.TicketingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
//@RequestMapping("ticket")
@Slf4j
//@CrossOrigin("**")
public class TicketController {

    private final TicketingService ticketingService;

    @Autowired
    public TicketController(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

   // http://localhost:5300/ticket/{eventId} get
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventByEventId(@PathVariable String eventId) {
        try {
            return new ResponseEntity<>(ticketingService.getEventById(eventId), HttpStatus.OK);
        }
        catch (EventNotFoundException e){

            log.error("Exception occured in TicketController->getEventByEventId");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon"+e,HttpStatus.CONFLICT);
        }
        catch (Exception ex){
            log.error("Exception occured in TicketController->getEventByEventId" + ex);
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }

    // http://localhost:5300/ticket/seat/{eventId} get
    @GetMapping("/seat/{eventId}")
    public ResponseEntity<?> getEventByEventIdforTickets(@PathVariable String eventId) {
        try {
            return new ResponseEntity<>(ticketingService.getEventByIdforTix(eventId), HttpStatus.OK);
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


    // http://localhost:5300/ticket/book/{eventId} get
    @GetMapping("/book/{eventId}")
    public ResponseEntity<?> bookTicketsnoSeats(@PathVariable String eventId) {
        try {
            return new ResponseEntity<>(ticketingService.bookTicketforNoSeat(eventId), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            log.error("Exception occured in TicketController->bookTicketswithnoSeats");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (Exception ex){
            log.error("Exception occured in TicketController->bookTicketswithnoSeats");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        } catch (SeatNotFoundException e) {
            log.error("Exception occured in TicketController->bookTicketswithnoSeats");
            return new ResponseEntity<>("All seats are booked",HttpStatus.CONFLICT);
        }

    }

    // http://localhost:5300/ticket/stream/book/{eventId} get
    @GetMapping("/stream/book/{eventId}")
    public ResponseEntity<?> bookTicketsonStream(@PathVariable String eventId) {
        try {
            return new ResponseEntity<>(ticketingService.bookTicketforforStreaming(eventId), HttpStatus.OK);
        }
        catch (EventNotFoundException e){
            log.error("Exception occured in TicketController->bookTicketsOnStream");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (Exception ex){
            log.error("Exception occured in TicketController->bookTicketsOnStream");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }

    // http://localhost:5300/ticket/{eventId}/{nid} get
    @GetMapping("/{eventId}/{nid}")
    public ResponseEntity<?> getTicket(@PathVariable String eventId,@PathVariable int nid)  {
    try{
        return new ResponseEntity<>(ticketingService.getSeat(eventId,nid), HttpStatus.OK);
    }
        catch ( EventNotFoundException s)
        {
            log.error("Exception occured in TicketController->getTicket");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }

        catch (Exception ex)
        {
        log.error("Exception occured in TicketController->getTicket");
        return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
         }
    }



    // http://localhost:5300/ticket/book/{eventId}/{nid} get
    @GetMapping("/book/{eventId}/{nid}")
    public ResponseEntity<?> getBookedTicket(@PathVariable String eventId,@PathVariable int nid) {
        try {
            return new ResponseEntity<>(ticketingService.bookedTicket(eventId, nid), HttpStatus.OK);
        }
        catch ( EventNotFoundException s)
        {
            log.error("Exception occured in TicketController->getBookedTicket");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (Exception ex)
        {
            log.error("Exception occured in TicketController->getBookedTicket");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }

    // http://localhost:5300/ticket/status/{eventId}/{nid} get
    @GetMapping("/status/{eventId}/{nid}")
    public ResponseEntity<?> getTicketStatus(@PathVariable String eventId,@PathVariable int nid) {
        try {
            return new ResponseEntity<>(ticketingService.ticketStatus(eventId, nid), HttpStatus.OK);
        }
        catch ( EventNotFoundException s)
        {
            log.error("Exception occured in TicketController->ticketStatus");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (Exception ex)
        {
            log.error("Exception occured in TicketController->ticketStatus");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }

    // http://localhost:5300/ticket/cancel/{eventId}/{nid} get
    @GetMapping("/cancel/{eventId}/{nid}")
    public ResponseEntity<?> cancelTicket(@PathVariable String eventId,@PathVariable int nid){
        try {
            return new ResponseEntity<>(ticketingService.cancelTicket(eventId, nid), HttpStatus.OK);
        }
        catch ( EventNotFoundException s)
        {
            log.error("Exception occured in TicketController->cancelTicket");
            return new ResponseEntity<>("The Event is not currently available. We will be back soon",HttpStatus.CONFLICT);
        }
        catch (Exception ex)
        {
            log.error("Exception occured in TicketController->cancelTicket");
            return new ResponseEntity<>("Unexpected Error happened. We will be back soon",HttpStatus.CONFLICT);
        }

    }


}
