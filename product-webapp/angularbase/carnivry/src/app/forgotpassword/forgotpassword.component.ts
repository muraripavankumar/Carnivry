import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Loginuser } from '../model/loginuser';
import { LoginService } from '../service/login.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent implements OnInit {

  constructor(private userservice: LoginService,public rtr:Router,private builder:FormBuilder,public matdialogref:MatDialogRef<ForgotpasswordComponent>) { }

  logindata:Loginuser;
  data:string;
  password:string;

  forgotFormGroup = new FormGroup({
    emailId: new FormControl('', [Validators.required,Validators.email])
  });
 
  ngOnInit(): void {
  }

  onSubmit(){
    localStorage.setItem("emailId",this.forgotFormGroup.value["emailId"]);
    this.data=localStorage.getItem("emailId");
    this.userservice.forgotPassword(this.data).subscribe(
      success => {
        alert("Check your Email for verification");
        window.localStorage.setItem('tgt', success.token);
         console.log(success);
       },
       error => {
         console.log(error);
       });
  }

  get emailId() {
    return this.forgotFormGroup.get('emailId');
  }

  onClose(){
    this.matdialogref.close();
  }
  getErrorMessage() {
    if (this.emailId.hasError('required')) {
      return 'You must enter a value';
    }

    return this.emailId.hasError('email') ? 'Not a valid email' : '';
  }

}
