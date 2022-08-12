import { HttpClient } from '@angular/common/http';
// import { CoreEnvironment } from '@angular/compiler/src/compiler_facade_interface';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegistrationService } from './registration.service';

@Injectable({
  providedIn: 'root'
})
export class SocialUserService {

// <<<<<<< HEAD
  private controllerUrl="/api/v1/SocialUser";
  private socialUserBaseUrl= environment.baseUrl+"/registration"+this.controllerUrl;
  
// =======
//   private socialUserBaseUrl= environment.baseUrl+"/registration/api/v1/SocialUser";
// >>>>>>> f1f6dde4c09d1c3c01c926533693aa2513f04a6f

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

  getGithubAvatar()
  {
    this.myClient.get<any>(this.socialUserBaseUrl+"/getGithubAvatar").subscribe(data=> this.regService.updateAvatarUrl(data.github_avatar));
  }
  getGoogleAvatar()
  {
    this.myClient.get<any>(this.socialUserBaseUrl+"/getGoogleAvatar").subscribe(data=> this.regService.updateAvatarUrl(data.google_avatar));
  }

}
