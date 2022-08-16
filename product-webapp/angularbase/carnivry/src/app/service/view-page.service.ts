import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Event } from '../model/event';

@Injectable({
  providedIn: 'root'
})
export class ViewPageService {
  display() {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient: HttpClient) { }

// <<<<<<< HEAD
 // viewEventurl="http://localhost:5300/ticket";
  private controllerUrl="/api/v1";
 private viewEventurl=environment.baseUrl+"/ticketservice"+this.controllerUrl;
// =======
//   viewEventurl=environment.baseUrl+"/ticketservice/api/v1"
// >>>>>>> f1f6dde4c09d1c3c01c926533693aa2513f04a6f

  getHostEventById(eventId:string) {
    return this.httpClient.get<any>(this.viewEventurl+ "/" + eventId);
  }

  getHostEventById1(url:any) {
    return this.httpClient.get<any>(this.viewEventurl+ "/" + url);
  }

  getHostEventforSeats(url:any){
    return this.httpClient.get<any>(this.viewEventurl+"/"+"seat/"+url)
  }

  
}
