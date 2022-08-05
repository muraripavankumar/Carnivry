import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from '../model/event';

@Injectable({
  providedIn: 'root'
})
export class ManagementService {

  constructor(private httpClient: HttpClient) { }

  managementUrl = "http://localhost:8081/api/v1";
  postHostEvent(eventData: Event) {
    return this.httpClient.post(this.managementUrl, eventData, { observe: 'response' });
  }
  getHostEventById(eventId: string) {
    return this.httpClient.get<any>(this.managementUrl+"/" + eventId);
  }
  updateHostEvent(eventData: Event) {
    console.log(eventData);
    return this.httpClient.patch<any>(this.managementUrl, eventData,{ observe: 'response' });
  }
  getAllEvents(){
    return this.httpClient.get<Event[]>(this.managementUrl);
  }
  getAllEventsByUserEmailId(userEmail:string){
    return this.httpClient.get<Event[]>(this.managementUrl+"/"+userEmail);
  }
}
