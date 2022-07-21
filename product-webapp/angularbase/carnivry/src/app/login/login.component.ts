import { F } from '@angular/cdk/keycodes';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private fb:FormBuilder,
             private regService:RegistrationService) { }

  loginForm:any;
  successMessage:any;
  failureMessage:any;

  ngOnInit(): void {

    this.loginForm= this.fb.group({
      email: new FormControl('',[Validators.required,Validators.email]),
      password: new FormControl('',[Validators.required,Validators.minLength(8),Validators.maxLength(20)])
    });
  }

  get f(){
    return this.loginForm.controls;
  }

  login(){

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
