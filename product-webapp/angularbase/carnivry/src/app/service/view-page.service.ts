import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from '../model/event';

@Injectable({
  providedIn: 'root'
})
export class ViewPageService {
  display() {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient: HttpClient) { }

  viewEventurl="http://localhost:5300/ticket"

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
