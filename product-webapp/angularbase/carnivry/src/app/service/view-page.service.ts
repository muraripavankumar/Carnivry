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

  getHostEventById() {
    return this.httpClient.get<any>(this.viewEventurl+ "/" + "22b8f962-a9ad-49da-b531-337fa8592d72");
  }

  getHostEventById1(url:any) {
    return this.httpClient.get<any>(this.viewEventurl+ "/" + url);
  }

  getHostEventforSeats(url:any){
    return this.httpClient.get<any>(this.viewEventurl+"/"+"seat/"+url)
  }

  
}
