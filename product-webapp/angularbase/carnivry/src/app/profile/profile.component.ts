import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegistrationService } from '../service/registration.service';
import { UpdateAddressDialogComponent } from '../update-address-dialog/update-address-dialog.component';
import { UpdateDOBDialogComponent } from '../update-dobdialog/update-dobdialog.component';
import { UpdateEmailDialogComponent } from '../update-email-dialog/update-email-dialog.component';
import { UpdateProfilePicDialogComponent } from '../update-profile-pic-dialog/update-profile-pic-dialog.component';

export interface EmailUpdateData {
  
  email: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit {

  constructor(private regService:RegistrationService, private dialog: MatDialog) { }
  email:any;
  avatarUrl:any;
  name:any;

  ngOnInit(): void {
    this.email=this.regService.getEmail();
    this.avatarUrl=this.regService.getAvatarUrl();
    setTimeout(()=>{
      if(this.avatarUrl===null)
      this.avatarUrl= "../../assets/S.jpg";
      this.name=this.regService.getName();
    },100)

    
  }

  updateEmailId(){
    let openDuration= '3000ms';
    const emailUpdation = this.dialog.open(UpdateEmailDialogComponent,{data: {email: this.email}});

    emailUpdation.afterClosed().subscribe(res=>{
      console.log("New email id ",res);
    });
  }

  updateProfilePic(){
    const profilePicUpdation= this.dialog.open(UpdateProfilePicDialogComponent,{data: {email: this.email}});

    profilePicUpdation.afterClosed().subscribe(res=>{
      console.log("New Pofile Picture",res);
    })
  }

  updateDOB(){
    const dobUpdation= this.dialog.open(UpdateDOBDialogComponent,{data: {email: this.email}});

    dobUpdation.afterClosed().subscribe(res=>{
      console.log("New DOB",res);
    })
  }

  updateAddress(){
    const addressUpdation= this.dialog.open(UpdateAddressDialogComponent,{data: {email: this.email}});

    addressUpdation.afterClosed().subscribe(res=>{
      console.log("New Address",res);
    })
  }

  
}


