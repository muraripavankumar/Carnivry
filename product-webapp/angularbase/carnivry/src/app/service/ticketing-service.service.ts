import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from '../model/event';
import { Seat } from '../model/seat';

@Injectable({
  providedIn: 'root'
})
export class TicketingServiceService {

  constructor(private httpClient: HttpClient) { }

  viewEventurl="http://localhost:5300/ticket"

  getTicket1(url:string,s:number){
    return this.httpClient.get<any>(this.viewEventurl+"/" + url+"/"+s)
  }

  bookticket(url:string){
    return this.httpClient.get<any>(this.viewEventurl+"/" +"book" +"/"+ url)
  }

  streamingBooking(url:string){
    return this.httpClient.get<any>(this.viewEventurl+"/"+"stream"+"/" +"book" +"/"+ url)
  }
}
