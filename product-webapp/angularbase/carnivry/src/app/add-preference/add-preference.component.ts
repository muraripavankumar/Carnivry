import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { EmailVerificationComponent } from '../email-verification/email-verification.component';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-add-preference',
  templateUrl: './add-preference.component.html',
  styleUrls: ['./add-preference.component.css']
})
export class AddPreferenceComponent implements OnInit {

  constructor(private regService: RegistrationService, private router:Router) 
  {
    this.name=regService.getName();
    this.email= regService.getEmail();
    this.authProvider= regService.getAuthProvider();
   }

  name:any;
  email:any;
  authProvider:any;
  showSelectedGenres=false;
  genres:any;
  warning:any;

  ngOnInit(): void {

    
    this.regService.getAllGenres().subscribe(data=>
      { this.genres= new Set(data)},
      error=>{
        console.error(error);
      })
  }
  formControl = new FormControl([]);

  show(){
    this.showSelectedGenres=!this.showSelectedGenres;
    console.log(this.formControl.value);
    console.log(this.email);
    console.log(this.authProvider);
  }

  addGenre(){
    let myGenres = {
      'email': this.email,
      'genres': this.formControl.value
    }
    console.log('myGenres : ');
    console.log(myGenres);
    let f= this.formControl.value;
    if(f.length>2)
    this.regService.addGenre(myGenres).subscribe(r=>{
      
      console.log(r);
      if(this.authProvider==='carnivry')
         this.router.navigate(['/registration/login']);
      else
         this.router.navigate(['/home']);
      
    },
      error=>{
        console.error(error);
      });
    else
      {
        this.warning="Please select atleast 3 genres";
        setTimeout(():void=> this.warning=null,5000);
      }

    }

  removeGenre(genre: string) {
    this.genres.delete(genre);
  }

}
