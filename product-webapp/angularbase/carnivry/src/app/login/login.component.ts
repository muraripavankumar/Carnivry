import { F } from '@angular/cdk/keycodes';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LoginService } from '../service/login.service';
import { RegistrationService } from '../service/registration.service';
import { ForgotpasswordComponent } from '../forgotpassword/forgotpassword.component';
import { Loginuser } from '../model/loginuser';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginuser: Loginuser;

  constructor(private fb:FormBuilder,
             private regService:RegistrationService,private userservice: LoginService,public rtr:Router,public matdialog:MatDialog) { }

  loginFormGroup:any;
  successMessage:any;
  failureMessage:any;

  ngOnInit(): void {

    this.loginFormGroup= this.fb.group({
      emailId: new FormControl('',[Validators.required,Validators.email]),
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
        window.localStorage.setItem('tgt', success.token);
        localStorage.setItem("emailId",this.loginFormGroup.value["emailId"]);
        localStorage.setItem("password",this.loginFormGroup.value["password"]);
      },
      error => {
        console.log(error);
      });
  }

  opendialouge(){
    this.matdialog.open(ForgotpasswordComponent,{
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
