import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UpdateData } from '../profile/profile.component';
import { RefreshingService } from '../service/refreshing.service';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-update-profile-pic-dialog',
  templateUrl: './update-profile-pic-dialog.component.html',
  styleUrls: ['./update-profile-pic-dialog.component.css']
})
export class UpdateProfilePicDialogComponent implements OnInit {
  profilePic: any;
  profilePicUrl: string | ArrayBuffer;

  constructor(private fb:FormBuilder, private refreshingService: RefreshingService,private regService:RegistrationService, @Inject(MAT_DIALOG_DATA) public data:UpdateData) { }

  profilePicForm:any;

  ngOnInit(): void {
    this.profilePicForm= this.fb.group({
      email: new FormControl('',[Validators.required,Validators.email]),
      profilePic: new FormControl('',[Validators.required])
    });
    this.f['email'].setValue(this.data.email);
  }

  get f(){
    return this.profilePicForm.controls;
  }

  public onFileChanged(event:any) {
    //Select File
    this.profilePic = event.target.files[0];

    var reader= new FileReader();

    reader.readAsDataURL(this.profilePic);
    reader.onload= (_event) => {
      this.profilePicUrl= reader.result + '';
      this.f['profilePic'].setValue(this.profilePicUrl);

    }
  }

  onSubmit(){
    console.log(this.profilePicForm.value);

    this.regService.addProfilePic(this.profilePicForm.value).subscribe((res: any)=>{
      this.refreshingService.notifyOther({refresh: true});
      console.log(res);
    },
      (error: any)=>{
      console.log(error);
    });
  }

}
