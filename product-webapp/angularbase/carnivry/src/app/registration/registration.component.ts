import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostUser } from '../model/post-user';
import { RegistrationService } from '../service/registration.service';
import Validation from '../validations/passwordMatcher';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private regService: RegistrationService,
              private router: Router,
              private fb:FormBuilder) { regService.updateAuthProvider('carnivry'); }


  postUser!: PostUser;
  successMessage:any;
  failureMessage:any;
  registerUserForm: any;
  
  ngOnInit(): void {

    this.registerUserForm= this.fb.group({
      name: new FormControl('',[Validators.required,Validators.minLength(4)]),
      email: new FormControl('',[Validators.required,Validators.email]),
      password: new FormControl('',[Validators.required,Validators.minLength(8),Validators.maxLength(20)]),
      matchingPassword: new FormControl('',[Validators.required])
    },
    {
      validators: [Validation.match('password', 'matchingPassword')]
    })
  }

  get f(){
    return this.registerUserForm.controls;
  }

  register(){

    
    this.postUser= this.registerUserForm.value;
    // console.log(this.postUser);

    this.regService.register(this.postUser).subscribe( r => {
      
          console.log( "User successfully registered");
          this.successMessage="Your data is successfully registered";
          console.log(r.status);
          this.regService.updateEmail(this.postUser.email);
          this.regService.updateName(this.postUser.name);
          // localStorage.setItem("authProvider",'carnivry');
          
          setTimeout(()=>{
            this.router.navigate(["/Carnivry/verifyEmail"]);
          },1000)
        
    },
     error=>{
      console.log(error);
      if(error.status==409)
      {
        this.failureMessage="User Already Exists";
        setTimeout(():void=>{
          this.failureMessage=null;
          this.registerUserForm.reset();
        },3000)  
        
      }
     });

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
