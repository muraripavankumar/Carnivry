import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Event } from '../model/event';

@Injectable({
  providedIn: 'root'
})
export class ManagementService {

  constructor(private httpClient: HttpClient) { }

  // managementUrl = "http://localhost:8081/api/v1";
  managementUrl = `${environment.baseUrl}/management/api/v1`;
  postHostEvent(eventData: Event) {
    return this.httpClient.post(this.managementUrl, eventData, { observe: 'response' });
  }
  getHostEventById(eventId: string) {
    return this.httpClient.get<any>(this.managementUrl+"/" + eventId);
  }
  updateHostEvent(eventData: Event) {
    return this.httpClient.patch<any>(this.managementUrl, eventData,{ observe: 'response' });
  }
  getAllEvents(){
    return this.httpClient.get<Event[]>(this.managementUrl);
  }
  getAllEventsByUserEmailId(userEmail:string){
    return this.httpClient.get<Event[]>(this.managementUrl+"/"+userEmail);
  }
  getPastEventsByUserEmailId(userEmail:string){
    return this.httpClient.get<Event[]>(this.managementUrl+'/past/'+userEmail);
  }
  getUpcomingEventsByUserEmailId(userEmail:string){
    return this.httpClient.get<Event[]>(this.managementUrl+'/upcoming/'+userEmail);
  }
}
