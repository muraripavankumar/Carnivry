import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ManagementService {

  constructor(private httpClient: HttpClient) { }

  managementUrl = "http://localhost:8081/api/v1";
  postHostEvent(eventData: Event) {
    // var options = { content: formData };
    // Display the key/value pairs
    // for (var pair of formData.entries()) {
    //   console.log(pair[0] + ', ' + pair[1]);
    // }
    console.log(eventData);
    return this.httpClient.post(this.managementUrl, eventData, { observe: 'response' });
  }
  getHostEventById(eventId: string) {
    return this.httpClient.get<any>(this.managementUrl + eventId);
  }
  updateHostEvent(eventData:Event) {
    return this.httpClient.patch<any>(this.managementUrl, eventData);
  }
}
