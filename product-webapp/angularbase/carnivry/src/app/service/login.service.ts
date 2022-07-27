import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Loginuser } from '../model/loginuser';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  emailId:string;
  
  constructor(private httpClient: HttpClient) { }
  //http://localhost:64200/api/v1/login
  userauthenticationbaseurl = "http://localhost:64200/api/v1";

  logincheck(data: Loginuser) {
    return this.httpClient.post<any>(this.userauthenticationbaseurl + "/login", data);
  }

  
   //http://localhost:64200/api/v1/forgotPassword    (update) 
   productbaseurl1: string = "http://localhost:64200/api/v1";

  forgotPassword(data:Loginuser) {
    // console.log(window.localStorage.getItem('tgt'));
   return this.httpClient.put<any>(this.userauthenticationbaseurl + "/forgotPassword",data);
  }

  //http://localhost:64200/userservice/forgot-password 
  baseurl = "http://localhost:64200/api/v1";

  emailLink(data:String){
    return this.httpClient.post<any>(this.userauthenticationbaseurl + "/emailLink/"+data, data);
  }

  updatePassword(password:string,token:string){}
  setMessage(data:string){
    this.emailId=data
  }

  getMessage(){
    return this.emailId
  }

  
}
