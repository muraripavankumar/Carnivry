import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Loginuser } from '../model/loginuser';
import { LoginService } from '../service/login.service';
import Validation from '../validations/passwordMatcher';


@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  
  logindata:Loginuser;
  email:string=localStorage.getItem("email");
  password:string;
  updatePasswordForm:FormGroup;
  newPassword:FormControl;
  cnewPassword:FormControl;
  constructor(private builder:FormBuilder,private userservice:LoginService,public rtr:Router) { }

  ngOnInit(): void {
    //this.emailId=this.userservice.getMessage()
    this.newPassword=new FormControl('', [Validators.required,Validators.maxLength(15),Validators.minLength(8)]);
    this.cnewPassword=new FormControl('', [Validators.required]);
    
    this.updatePasswordForm=this.builder.group({
      'newPassword':this.newPassword,
      'cnewPassword':this.cnewPassword,
    },
    {
      validators: [Validation.match('newPassword', 'cnewPassword')]
    });
  }

  
  get f(){
    return this.updatePasswordForm.controls;
  }
  updatePassword(){
    console.log(this.updatePasswordForm.value);
    localStorage.setItem("password",this.updatePasswordForm.value["cnewPassword"]);
    this.password=localStorage.getItem("password");
    console.log(this.password);
    console.log(this.email);
     this.logindata=new Loginuser(this.email,this.password);
     this.userservice.forgotPassword(this.logindata).subscribe(
       response => {
         console.log("Password updated successfully : ");
         alert("Password updated successfully!! ")
         console.log(response);
         this.rtr.navigate(['Carnivry/login']);
       },
       error => {
        console.log(error);
      });
  }


}
