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

  viewEventurl=environment.baseUrl+"/ticketservice/api/v1"

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
