import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Loginuser } from '../model/loginuser';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  emailId:string;
  
  constructor(private httpClient: HttpClient) { }
  //http://localhost:64200/api/v1/login
// <<<<<<< HEAD
  // userauthenticationbaseurl = "http://localhost:64200/api/v1";
  // baseUrl= environment.baseUrl+"/userauth/api/v1";
  private controllerUrl="/api/v1";
  private loginUrl= environment.baseUrl+"/userauth"+this.controllerUrl;
// =======
//   userauthenticationbaseurl = "http://localhost:64200/api/v1";
//   baseUrl= environment.baseUrl+"/userauth/api/v1"
// >>>>>>> f1f6dde4c09d1c3c01c926533693aa2513f04a6f

  logincheck(data: Loginuser) {
    return this.httpClient.post<any>(this.loginUrl + "/login", data);
  }

  
   //http://localhost:64200/api/v1/forgotPassword    (update) 
   productbaseurl1: string = "http://localhost:64200/api/v1";

  forgotPassword(data:Loginuser) {
    // console.log(window.localStorage.getItem('tgt'));
    var reqHeader=new HttpHeaders().set('Authorization','Bearer '+window.localStorage.getItem('token'));
   return this.httpClient.put<any>(this.loginUrl + "/forgotPassword",data,{'headers':reqHeader});
  }

  //http://localhost:64200/api/v1/forgot-password 
  baseurl = "http://localhost:64200/api/v1";

  emailLink(data:String){
    return this.httpClient.post<any>(this.loginUrl + "/emailLink/"+data, data);
  }

  updatePassword(password:string,token:string){}
  setMessage(data:string){
    this.emailId=data
  }

  getMessage(){
    return this.emailId
  }

  
}
