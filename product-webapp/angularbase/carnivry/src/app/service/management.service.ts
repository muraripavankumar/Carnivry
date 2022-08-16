import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Event } from '../model/event';

@Injectable({
  providedIn: 'root'
})
export class ManagementService {

  constructor(private httpClient: HttpClient) { }

  
  private cotrollerUrl = "/api/v1";
  private managementUrl = environment.baseUrl + "/management" + this.cotrollerUrl;


  postHostEvent(eventData: Event) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.post(this.managementUrl, eventData, { observe: 'response', 'headers': reqHeader });
  }
  getHostEventById(eventId: string) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.get<any>(this.managementUrl + "/" + eventId, { 'headers': reqHeader });
  }
  updateHostEvent(eventData: Event) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.patch<any>(this.managementUrl, eventData, { observe: 'response', 'headers': reqHeader });
  }
  getAllEvents() {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.get<Event[]>(this.managementUrl, { 'headers': reqHeader });
  }
  getAllEventsByUserEmailId(userEmail: string) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    console.log(reqHeader);
    return this.httpClient.get<Event[]>(this.managementUrl + "/" + userEmail, { 'headers': reqHeader });
  }
  getPastEventsByUserEmailId(userEmail: string) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.get<Event[]>(this.managementUrl + '/past/' + userEmail, { 'headers': reqHeader });
  }
  getUpcomingEventsByUserEmailId(userEmail: string) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.get<Event[]>(this.managementUrl + '/upcoming/' + userEmail, { 'headers': reqHeader });
  }
  updateNoOfLikes(eventId: string, flag: boolean) {
    var reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
    return this.httpClient.patch<any>(this.managementUrl + '/likes/' + eventId + '/' + flag, { 'headers': reqHeader });
  }
}
