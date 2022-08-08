import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MyGenreDialogComponent } from '../my-genre-dialog/my-genre-dialog.component';
import { RegistrationService } from '../service/registration.service';
import { UpdateAddressDialogComponent } from '../update-address-dialog/update-address-dialog.component';
import { UpdateDOBDialogComponent } from '../update-dobdialog/update-dobdialog.component';
import { UpdateEmailDialogComponent } from '../update-email-dialog/update-email-dialog.component';
import { UpdateProfilePicDialogComponent } from '../update-profile-pic-dialog/update-profile-pic-dialog.component';

export interface UpdateData {
  
  email: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit {

  constructor(private regService:RegistrationService, private dialog: MatDialog, private router: Router) { }
  email:any;
  avatarUrl:any;
  name:any;

  ngOnInit(): void {
    this.email=this.regService.getEmail();
    this.avatarUrl=this.regService.getAvatarUrl();
    console.log(this.avatarUrl);
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

  myGenre(){
    const genreUpdation= this.dialog.open(MyGenreDialogComponent,{data: {email: this.email}});

    genreUpdation.afterClosed().subscribe(res=>{
      console.log(res);
    })
  }

  getPostedEvents(){
     this.router.navigate(['/posted-events']);
  }
  
  getPastEvents(){
    this.router.navigate(['/past-events']);
 }

 getUpcomingEvents(){
  this.router.navigate(['/upcoming-events']);
}

  
}


