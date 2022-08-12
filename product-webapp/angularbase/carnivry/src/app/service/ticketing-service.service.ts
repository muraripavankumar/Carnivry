import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Event } from '../model/event';
import { Seat } from '../model/seat';

@Injectable({
  providedIn: 'root'
})
export class TicketingServiceService {

  constructor(private httpClient: HttpClient) { }

// <<<<<<< HEAD
  // viewEventurl=environment.baseUrl+"ticketservice/api/v1";
  private controllerUrl="/api/v1";
  private viewEventurl=environment.baseUrl+"/ticketservice"+this.controllerUrl;
// =======
//   viewEventurl=environment.baseUrl+"/ticketservice/api/v1"
// >>>>>>> f1f6dde4c09d1c3c01c926533693aa2513f04a6f



  getTicket1(url:string,s:number){
    return this.httpClient.get<any>(this.viewEventurl+"/" + url+"/"+s)
  }

  bookticket(url:string){
    return this.httpClient.get<any>(this.viewEventurl+"/" +"book" +"/"+ url)
  }

  streamingBooking(url:string){
    return this.httpClient.get<any>(this.viewEventurl+"/"+"stream"+"/" +"book" +"/"+ url)
  }

  bookseat(url:string,s:number){
    return this.httpClient.get<any>(this.viewEventurl+"/book/"+url+"/"+s)
  }
}
