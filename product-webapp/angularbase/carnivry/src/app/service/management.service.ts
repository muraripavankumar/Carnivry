import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ManagementService {

  constructor(private httpClient:HttpClient) { }
  
  managementUrl:"http://localhost:8081/api/v1";
  postHostEvent(formData: FormData){
    console.log("inside service");
    console.log(formData);
    return this.httpClient.post<any>(this.managementUrl,formData,{observe: 'response'});
  }
}
