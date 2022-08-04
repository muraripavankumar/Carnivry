import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UpdateData } from '../profile/profile.component';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-my-genre-dialog',
  templateUrl: './my-genre-dialog.component.html',
  styleUrls: ['./my-genre-dialog.component.css']
})
export class MyGenreDialogComponent implements OnInit {

  email:any;
  myGenres:any;
  allGenres:any;
  willChange:boolean=false;
  formControl: any;
  warning: string;
  constructor( @Inject(MAT_DIALOG_DATA) public data:UpdateData, private regService: RegistrationService) { }

  
  ngOnInit(): void {
    this.email= this.data.email;
    console.log(this.email);

    this.regService.getGenres(this.email).subscribe(res=>{
      this.myGenres= res;
    })

    this.regService.getAllGenres().subscribe(data=>
      { this.allGenres= new Set(data)},
      error=>{
        console.error(error);
      })

      this.formControl = new FormControl([]);
  }

  changeGenres(){
     this.willChange= !this.willChange;
   }
 
   onSubmit(){
    let addGenres = {
      'email': this.email,
      'genres': this.formControl.value
    }

    let f= this.formControl.value;
    if(f.length>2)
    this.regService.addGenre(addGenres).subscribe(r=>{
      console.log(r);  
      
      this.regService.getGenres(this.email).subscribe(res=>{
        this.myGenres= res;
      })
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
    this.allGenres.delete(genre);
  }

}
