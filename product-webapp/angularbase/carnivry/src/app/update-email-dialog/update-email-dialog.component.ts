import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import {  UpdateData } from '../profile/profile.component';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-update-email-dialog',
  templateUrl: './update-email-dialog.component.html',
  styleUrls: ['./update-email-dialog.component.css']
})
export class UpdateEmailDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data:UpdateData
  , private fb:FormBuilder
  , private regService:RegistrationService) { }

  newEmailId:any;
  password:any;
  newEmailForm:any;
  response:any;
  resendBtnEnable:boolean= false;
  ngOnInit(): void {
    console.log(this.data.email);
    this.response= document.getElementById("response");

    this.newEmailForm= this.fb.group({
      newEmail: new FormControl('',[Validators.required,Validators.email]),
      oldEmail: new FormControl('',[Validators.required,Validators.email])
    });
    this.f['oldEmail'].setValue(this.data.email);
  }
  get f(){
    return this.newEmailForm.controls;
  }

  onSubmit(){
    console.log(this.newEmailForm.value);
    this.regService.sendNewEmailVerificationLink(this.newEmailForm.value).subscribe(res=>{
      console.log(res);
      
      var checkNewEmailVerification= setInterval(()=>{
        this.regService.isNewEmailVerified(this.newEmailForm.value).subscribe(res=>{
          if(res==="true")
          {
            console.log(res);
            var closeBtn= document.getElementById("closeBtn").click(); 
            clearInterval(checkNewEmailVerification);
          }
        })
      },5000)
    }
    ,error=>{
      console.log(error);
    })
    this.resendBtnEnable=true;
    
    var mainText= document.createElement('h1');
    mainText.innerText= "Email verification link sent to "+ this.f['newEmail'].value;
    this.response.appendChild(mainText);
    
    
    
    
    
  }

  sendVerificationLink(){
   
    var resendResponse= document.getElementById('resendResponse');
    
    this.regService.sendNewEmailVerificationLink(this.newEmailForm.value).subscribe(res=>{
      console.log(res);
      resendResponse.innerText= "Email Verification link Resended";
    }
    ,error=>{
      console.log(error);
    })

  }

}
