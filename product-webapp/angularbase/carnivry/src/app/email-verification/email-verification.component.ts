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

    let checkVerification= setInterval(()=>this.regService.isVerified(this.email).subscribe(r=>{
      console.log(r);
      this.isVerified= r;
      if(r===true)
      {
        
        console.log("Email veridfied");
        this.router.navigate(["/add-preference"]);
        clearInterval(checkVerification);
      }}), 5000);
  }

  isVerified:any;
  verificationMessage:any;
  emailMessage:any;
  email:any;
  // isVerified(){
    
  //   console.log(this.email);
  //   if(this.email!=null)
  //   this.regService.isVerified(this.email).subscribe(r=>{
  //     console.log(r);
  //     if(r===true)
  //     {
  //       this.verificationMessage="Your email is Verified";
  //       console.log("Email veridfied");
  //       this.router.navigate(["/Carnivry/addPreference"]);
  //     }
  //     else
  //     this.verificationMessage="Please click on the verification link sent on email";
  //   },
  //   error => {
  //     console.log(error);
  //   });

  // }
  resendEmail(){
    if(this.email!=null)
    this.regService.resendVerificationEmail(this.email).subscribe(r=>{
      console.log("vesrification link resended")
      this.emailMessage="Email Verification link resended";
    },
    error => {
      console.log(error);
    });
  }
}
