import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Loginuser } from '../model/loginuser';
import { LoginService } from '../service/login.service';
import { ConfirmedValidator } from '../validations/confirmed.validator';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

 
  logindata:Loginuser;
  emailId:string=localStorage.getItem("emailId");
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
      validator: ConfirmedValidator("newPassword", "cnewPassword")
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
    console.log(this.emailId);
     this.logindata=new Loginuser(this.emailId,this.password);
     this.userservice.resetPassword(this.logindata).subscribe(
       response => {
         console.log("Password updated successfully : ");
         alert("Password updated successfully!! ")
         console.log(response);
         this.rtr.navigate(['Carnivry/login']);
       })
  }


}
