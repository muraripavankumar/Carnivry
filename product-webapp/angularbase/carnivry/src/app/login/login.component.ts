import { F } from '@angular/cdk/keycodes';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LoginService } from '../service/login.service';
import { RegistrationService } from '../service/registration.service';
import { Loginuser } from '../model/loginuser';
import { EmailLinkComponent } from '../email-link/email-link.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginuser: Loginuser;

  constructor(private fb:FormBuilder,private registration:RegistrationService,
             private regService:RegistrationService,private userservice: LoginService,public rtr:Router,public matdialog:MatDialog) { }

  loginFormGroup:any;
  successMessage:any;
  failureMessage:any;

  ngOnInit(): void {

    this.loginFormGroup= this.fb.group({
      email: new FormControl('',[Validators.required,Validators.email]),
      password: new FormControl('',[Validators.required,Validators.minLength(8),Validators.maxLength(20)])
    });
  }

  get f(){
    return this.loginFormGroup.controls;
  }

  loginCheck(){
    window.localStorage.clear();
    this.loginuser = this.loginFormGroup.value;
    this.userservice.logincheck(this.loginuser).subscribe(
      success => {
       this.rtr.navigate(['Carnivry/home']);
        console.log(success);
        alert("Logged Successfully!!");
         this.registration.updateToken(success.token);
         this.regService.updateEmail(this.loginFormGroup.value["email"]);
      },
      error => {
        console.log(error);
      });
  }

  opendialouge(){
    this.matdialog.open(EmailLinkComponent,{
      height:'50%',
      width:'40%'
    })
  }
  
  signinGoogle(){
    this.regService.googleLogin();
    this.regService.updateAuthProvider('google');
    
  }

  signinGithub(){
    this.regService.githubLogin();
    this.regService.updateAuthProvider('github');
    
  }

}
