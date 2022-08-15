import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostUser } from '../model/post-user';
import { RegistrationService } from '../service/registration.service';
import { SocialUserService } from '../service/social-user.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {

  private email:any;
  private name:any;
  private isPresent:boolean=false;
  constructor(private route: ActivatedRoute,
    private router: Router,
    private regService: RegistrationService,
    private socialService: SocialUserService) {
      
      this.route.queryParams.subscribe(p => {
          
        console.log(localStorage.getItem("authProvider"));
        console.log("code= ",p['code']);

          if(localStorage.getItem("authProvider")==='github'){
             
            
            this.regService.githubFetchToken(p['code'], p['state']).subscribe(data => {
              this.regService.updateToken(data.accessToken);
              socialService.getGithubUsername();
              socialService.getName();
              socialService.getGithubAvatar();
              
              setTimeout(()=>{
                this.name=regService.getName();
                this.email=regService.getEmail();
                console.log("name: ",this.name);
                console.log("email: ",this.email);
                this.isUserPresent(this.email);
               
              },100);

              setTimeout(()=>{
                console.log(this.isPresent);
                if(this.isPresent===false)
                   {
                    this.registerSocialUser();
                    this.router.navigate(['/add-preference']);
                   }
                else
                  {
                    this.router.navigate(['']);
                  }
              },200);
              
            });
          }
          else if(localStorage.getItem("authProvider")==='google'){
            
            
            this.regService.googleFetchToken(p['code'], p['state']).subscribe(data => {
              this.regService.updateToken(data.accessToken);
              socialService.getEmail();
              socialService.getName();
              socialService.getGoogleAvatar();
              
              setTimeout(()=>{
                this.name=regService.getName();
                this.email=regService.getEmail();
                console.log("name: ",this.name);
                console.log("email: ",this.email);
                this.isUserPresent(this.email);
               
              },100);
              
              setTimeout(()=>{
                console.log(this.isPresent);
                if(this.isPresent===false)
                   {
                    this.registerSocialUser();
                    this.router.navigate(['/add-preference']);
                   }
                else
                  {
                    this.router.navigate(['']);
                  }
              },200);
            });
          }
      });
  }
     

  ngOnInit(): void {
  }

  registerSocialUser()
     {
       let socialUser= new PostUser(this.name,this.email,null,"","");
       this.regService.registerSocialUser(socialUser).subscribe(r=>{
         console.log(r);
         
       },error=>{
        if(error.status===409){
          this.router.navigate(['/home']);
        }
       })
      }
      
  
      isUserPresent(email:string)
      {
      
       console.log(email);
       this.regService.isUserPresent(email).subscribe(r=>{
         this.isPresent= r;
         console.log(r);
         
       },
       error=>{
         console.log(error);
       });
       
       }

}
