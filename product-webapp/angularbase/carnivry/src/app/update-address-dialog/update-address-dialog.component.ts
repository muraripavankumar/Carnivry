import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UpdateData } from '../profile/profile.component';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-update-address-dialog',
  templateUrl: './update-address-dialog.component.html',
  styleUrls: ['./update-address-dialog.component.css']
})
export class UpdateAddressDialogComponent implements OnInit {
  addressForm: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data:UpdateData
  , private fb:FormBuilder
  , private regService:RegistrationService) { }

  ngOnInit(): void {
    this.addressForm= this.fb.group({
      email: new FormControl('',[Validators.required,Validators.email]),
      house: new FormControl('',[Validators.required]),
      street: new FormControl('',[Validators.required,Validators.minLength(4)]),
      landmark: new FormControl('',[]),
      city: new FormControl('',[Validators.required]),
      state: new FormControl('',[Validators.required,Validators.minLength(3)]),
      country: new FormControl('',[Validators.required,Validators.minLength(4)]),
      pincode: new FormControl('',[Validators.required,Validators.pattern("[0-9]{6}")]),
    });

    this.f['email'].setValue(this.data.email);
  }

  
  public get f(){
    return this.addressForm.controls;
  }
  

  onSubmit(){
    console.log(this.addressForm.value);

    this.regService.addAddress(this.addressForm.value).subscribe(res=>{
      console.log(res);
    },
    error=>{
      console.log(error);
    })
  }

}
