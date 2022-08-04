import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { tap } from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import { LandingPageComponent } from '../landing-page/landing-page.component';
import { DialogBoxChooseCityComponent } from '../dialog-box-choose-city/dialog-box-choose-city.component';



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  headers={
    observe: 'response' as 'response'
};


  recommendData: any;
recommendList: any;
recommendPosterList: any;

  search: String;

  constructor(public dialog: MatDialog) { }

  openDialog() {
    this.dialog.open(DialogBoxChooseCityComponent);
  }

  ngOnInit(): void {
  }

}
