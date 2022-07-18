import { HttpClient } from '@angular/common/http';
import { CoreEnvironment } from '@angular/compiler/src/compiler_facade_interface';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegistrationService } from './registration.service';

@Injectable({
  providedIn: 'root'
})
export class SocialUserService {

  private socialUserBaseUrl= environment.socialUserBaseUrl;

  constructor(private myClient: HttpClient,
              private regService: RegistrationService) { }

  getName()
  {
    console.log("in social service get name");
    this.myClient.get<any>(this.socialUserBaseUrl+"/getName").subscribe(data=> this.regService.updateName(data.name));
   
  }
  
  getEmail()
  {
    console.log("in social service get email");
    this.myClient.get<any>(this.socialUserBaseUrl+"/getEmail").subscribe(data=> this.regService.updateEmail(data.email));
  }
  getGithubUsername()
  {
    this.myClient.get<any>(this.socialUserBaseUrl+"/getGithubUsername").subscribe(data=> this.regService.updateEmail(data.username));
  }
}