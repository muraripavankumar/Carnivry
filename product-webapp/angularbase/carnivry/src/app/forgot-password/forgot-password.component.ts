import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
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
  constructor(private builder:FormBuilder,private userservice:LoginService,public router:Router,private snackbar: MatSnackBar) { }

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
         this.snackbar.open('Password updated successfully !!', ' ', {
          duration: 3000
        });
         console.log(response);
         this.router.navigate(['/registration/login']);
       },
       error => {
        console.log(error);
        this.snackbar.open('Sorry! Password could not be uploaded. Please try again. !!', ' ', {
          duration: 3000
        });
      });
  }


}
