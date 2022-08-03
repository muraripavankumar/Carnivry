import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmailUpdateData } from '../profile/profile.component';

@Component({
  selector: 'app-update-email-dialog',
  templateUrl: './update-email-dialog.component.html',
  styleUrls: ['./update-email-dialog.component.css']
})
export class UpdateEmailDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data:EmailUpdateData) { }

  newEmailId:any;
  password:any;
  ngOnInit(): void {
    console.log(this.data.email);
  }

}
