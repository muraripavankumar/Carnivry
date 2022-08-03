import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UpdateData } from '../profile/profile.component';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-update-dobdialog',
  templateUrl: './update-dobdialog.component.html',
  styleUrls: ['./update-dobdialog.component.css']
})
export class UpdateDOBDialogComponent implements OnInit {

  dobForm: any;
  minDate: string;
  maxDate: string;

  constructor(@Inject(MAT_DIALOG_DATA) public data:UpdateData
  , private fb:FormBuilder
  , private regService:RegistrationService) {
    const currentYear = new Date().getFullYear();
    this.minDate = (currentYear-100)+"-01-01";
    this.maxDate = (currentYear-18)+"-12-31";
   }

  ngOnInit(): void {
    this.dobForm= this.fb.group({
      email: new FormControl('',[Validators.required,Validators.email]),
      dob: new FormControl('',[Validators.required]),
      
    });

    this.f['email'].setValue(this.data.email);
  }

  
  public get f(){
    return this.dobForm.controls;
  }
  

  onSubmit(){
    console.log(this.dobForm.value);

    this.regService.addDOB(this.dobForm.value).subscribe(res=>{
      console.log(res);
    },
    error=>{
      console.log(error);
    })
  }

}
