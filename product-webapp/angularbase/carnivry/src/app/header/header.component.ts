import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, tap } from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import { LandingPageComponent } from '../landing-page/landing-page.component';
import { DialogBoxChooseCityComponent } from '../dialog-box-choose-city/dialog-box-choose-city.component';
import { Router } from '@angular/router';
import {map, startWith} from 'rxjs/operators';




@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  signIn="";
  search: String;
  city="";
  login=false;

  allEventsData: any;
  allEventsList: any;
  allEventsPosterList: any;

  searchEventList: any=[];

  headers={
    observe: 'response' as 'response'
};


  myControl = new FormControl('');
    filteredOptions: Observable<string[]>;

  constructor(public dialog: MatDialog, private router: Router) { 

    this.search="";

    if(sessionStorage.getItem('city')==null){
      this.city="Choose city";
    }
    else{
      this.city=sessionStorage.getItem('city');
    }


    sessionStorage.setItem('username', "");

    if(sessionStorage.getItem('username')==""){
      this.signIn="SignIn/Login";
      this.login=false;
    }
    else{
      this.signIn=sessionStorage.getItem('username');
      this.login=true;
    }
  }

  openDialog() {
    console.log("City clicked");
    this.dialog.open(DialogBoxChooseCityComponent);
  }

  logo(){
    console.log("Logo clicked");
    this.router.navigate(['']);
  }

  addEvent(){
    console.log("Add event clicked");
    this.router.navigate(['host-event']);
  }

  SignInLogin(){
    console.log("Button value: "+this.signIn);
    if(this.signIn=="SignIn/Login"){
    this.router.navigate(['/registration/register']);
    }
  }

  home(){
    this.router.navigate(['']);
  }


// updateSearch(search: String){
//   console.log("Update search list method called : "+search);

  
//     // this.allEventsData=[];
//     // this.allEventsPosterList=[];
    
//     // this.allEventsData = sessionStorage.getItem("allshows");
  
//     // var allEventsJSON: any = this.allEventsData!==null ? JSON.parse(this.allEventsData): this.allEventsData;

//     // for(var i=0; i<allEventsJSON?.length; i++){
//     //   this.allEventsPosterList.push(allEventsJSON[i]);
//     // }

//     // this.allEventsList=this.allEventsPosterList;
//     this.searchEventList=[];

//     for(var i=0; i<this.allEventsList.length; i++){
//       if(this.allEventsList[i].includes(search) && search!="" && !this.searchEventList.includes(this.allEventsList[i])){
//         this.searchEventList.push(this.allEventsList[i]);
//       }

//     }
//   console.log("Search list: "+ this.searchEventList.length);
// }

// private _filter(value: string): string[] {
//   const filterValue = value.toLowerCase();

//   return this.allEventsList.filter((title: string) => title.toLowerCase().includes(filterValue));
// }


private _filter(value: string): string[] {
  
  this.allEventsData=[];
  this.allEventsPosterList=[];
  this.allEventsList=[];
  this.allEventsData = sessionStorage.getItem("allshows");
  
  var allEventsJSON: any = this.allEventsData!==null ? JSON.parse(this.allEventsData): this.allEventsData;

  for(var i=0; i<allEventsJSON?.length; i++){
    this.allEventsPosterList.push(allEventsJSON[i]);
  }

  this.allEventsList=this.allEventsPosterList;
  console.log("All Events length: "+this.allEventsList.length);

  const filterValue = value.toLowerCase();

  return this.allEventsList.filter((title: string) => title.toLowerCase().includes(filterValue));
}

  ngOnInit(): void {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }
}
