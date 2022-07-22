import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-email-verification',
  templateUrl: './email-verification.component.html',
  styleUrls: ['./email-verification.component.css']
})
export class EmailVerificationComponent implements OnInit {

  constructor(private regService: RegistrationService,
              private router:Router) { }

  ngOnInit(): void {
    this.email= this.regService.getEmail();
  }

  verificationMessage:any;
  emailMessage:any;
  email:any;
  isVerified(){
    
    console.log(this.email);
    if(this.email!=null)
    this.regService.isVerified(this.email).subscribe(r=>{
      console.log(r);
      if(r===true)
      {
        this.verificationMessage="Your email is Verified";
        this.router.navigate(["/Carnival/addPreference"]);
      }
      else
      this.verificationMessage="Please click on the verification link sent on email";
    },
    error => {
      console.log(error);
    });

  }
  resendEmail(){
    if(this.email!=null)
    this.regService.resendVerificationEmail(this.email).subscribe(r=>{
      this.emailMessage=r;
    },
    error => {
      console.log(error);
    });
  }
}
