import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Loginuser } from '../model/loginuser';
import { LoginService } from '../service/login.service';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-email-link',
  templateUrl: './email-link.component.html',
  styleUrls: ['./email-link.component.css']
})
export class EmailLinkComponent implements OnInit {

  constructor(private userservice: LoginService,public router:Router,private builder:FormBuilder,public matdialogref:MatDialogRef<EmailLinkComponent>,private registration:RegistrationService,private snackbar: MatSnackBar) { }

  logindata:Loginuser;
  data:string;
  password:string;

  forgotFormGroup = new FormGroup({
    email: new FormControl('', [Validators.required,Validators.email])
  });
 
  ngOnInit(): void {
  }

  onSubmit(){
    localStorage.setItem("email",this.forgotFormGroup.value["email"]);
    this.data=localStorage.getItem("email");
    this.userservice.emailLink(this.data).subscribe(
      success => {
        this.snackbar.open('Link has been sent,Verify your email !!', ' ', {
          duration: 3000
        });
        window.localStorage.setItem('token', success.token);
        this.registration.updateToken(success.token);
         console.log(success);
       },
       error => {
         console.log(error);
         this.snackbar.open('Invalid or Non-registered Email !!', ' ', {
          duration: 3000
        });
       });
  }

  get email() {
    return this.forgotFormGroup.get('email');
  }

  onClose(){
    this.matdialogref.close();
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
}
