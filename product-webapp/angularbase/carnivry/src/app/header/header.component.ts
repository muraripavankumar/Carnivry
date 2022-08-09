import { HttpClient } from '@angular/common/http';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, tap } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { LandingPageComponent } from '../landing-page/landing-page.component';
import { DialogBoxChooseCityComponent } from '../dialog-box-choose-city/dialog-box-choose-city.component';
import { Router } from '@angular/router';
import { map, startWith } from 'rxjs/operators';
import { RegistrationService } from '../service/registration.service';





@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  signIn = "";
  search: String;
  city = "";
  login = false;

  allEventsList: any[];
  filteredList: any[]=[];
  titleList: any[]=[];

  searchText: string='';
  
  

  
  headers = {
    observe: 'response' as 'response'
  };


  myControl = new FormControl('');
  filteredOptions: Observable<string[]>;

  constructor(public dialog: MatDialog, private router: Router, private registrationService: RegistrationService, private http: HttpClient) {

    this.search = "";

    if (sessionStorage.getItem('city') == null) {
      this.city = "Choose city";
    }
    else {
      this.city = sessionStorage.getItem('city');
    }


    
    // const tokenValue = localStorage.getItem('token');
    const tokenValue= "dksjhksjdf";
    console.log('token value : ' + tokenValue);

    if (tokenValue === null) {
      this.signIn = "SignIn/Login";
      this.login = false;
    }
    else {
      // this.signIn = registrationService.getName();
      this.signIn="Garima";

      this.login = true;
    }
    console.log('signIn value' + this.signIn);
  }

  openDialog() {
    console.log("City clicked");
    this.dialog.open(DialogBoxChooseCityComponent);
  }

  logo() {
    console.log("Logo clicked");
    this.router.navigate(['']);
  }

  addEvent() {
    console.log("Add event clicked");
    this.router.navigate(['/host-event']);
  }

  SignInLogin() {
    console.log("Button value: " + this.signIn);
    if (this.signIn == "SignIn/Login") {
      this.router.navigate(['/registration/register']);
    }
  }

  home() {
    this.router.navigate(['']);
  }

  logout() {
    console.log('log out triggered');
    this.registrationService.logout().subscribe(() => {
      this.signIn='SignIn/Login';
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigate(['/landing-page']);
    });
  }

  onSearchTextEntered(searchValue: string){
    this.searchText=searchValue;
    this.filteredList=[];
    this.titleList=[];
    // console.log("Entered value"+ this.searchText);

    this.http.get<Event[]>('http://localhost:8082/api/v1/all-events', this.headers)
      .subscribe(
        (data) => {
          this.allEventsList = data.body;
          console.log("All events in header page: " + this.allEventsList.length);
          
          for(var i=0; i<this.allEventsList.length; i++){
            if(this.allEventsList[i].title.toLowerCase().includes(this.searchText.toLowerCase()) && this.searchText!=""){
              this.filteredList.push(this.allEventsList[i]);
            }
          }
          this.titleList=this.filteredList;
        }
      )

    // this.allEventsList=["Bramastra", "Jug jug jeeyo", "The Kashmir Files", "Gangubai Kathiawadi", "Bachchhan Paandey", "Shamshera", "Badhaai Do", "Jhund"];

    // for(var i=0; i<this.allEventsList.length; i++){
    //   if(this.allEventsList[i].toLowerCase().includes(this.searchText.toLowerCase()) && this.searchText!=""){
    //     this.filteredList.push(this.allEventsList[i]);
    //   }
    // }
    // this.titleList=this.filteredList;

    // console.log("titleList length: "+ this.titleList.length);
    
  }

  openViewPage(eventId: any){
    this.titleList=[];
    this.router.navigate(['/view-page',eventId]);
  }

  


  ngOnInit(): void {
  }
}
