package com.stackroute.Service;

import com.stackroute.Exceptions.EventNotFoundException;

import com.stackroute.Exceptions.SeatNotFoundException;
import com.stackroute.model.Event;
import com.stackroute.model.Seat;


public interface TicketingService {


    Event getEventById(String eventId) throws Exception;

    Event getEventByIdforTix(String eventId) throws Exception;

    Seat processTicket(String eventId,int nid) throws EventNotFoundException;

    Seat getSeat(String eventId,int nid) throws EventNotFoundException;

    Seat bookedTicket(String eventId,int nid) throws EventNotFoundException;

    Seat ticketStatus(String eventId,int nid) throws EventNotFoundException;

    Seat cancelTicket(String eventId, int nid) throws EventNotFoundException;

    Event bookTicketforNoSeat(String eventId) throws EventNotFoundException, SeatNotFoundException;

    int bookTicketforforStreaming(String eventId) throws EventNotFoundException;

}
