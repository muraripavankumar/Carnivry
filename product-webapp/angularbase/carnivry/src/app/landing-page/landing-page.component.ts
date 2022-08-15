import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MatAccordion } from '@angular/material/expansion';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Event } from '../model/event';
import { ManagementService } from '../service/management.service';

interface carouselImage {
  imageSrc: string;
  imageAlt: string;
}


@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {
  controllerUrl = "/api/v1";
  apiUrl = "/suggestion";
  baseUrl = environment.baseUrl;
  suggestionUrl = this.baseUrl + this.apiUrl + this.controllerUrl;
  reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + window.localStorage.getItem('token'));



  @Input() controls = true;



  images = [
    {
      imageSrc:
        '../assets/offer (1).png',
      imageAlt: 'nature1',
    },
    {
      imageSrc:
        '../assets/offer (2).png',
      imageAlt: 'nature2',
    },
    {
      imageSrc:
        '../assets/offer (3).png',
      imageAlt: 'person1',
    }
  ]

  search: String;

  range = new FormGroup({
    start: new FormControl(null),
    end: new FormControl(null),
  });

  @ViewChild(MatAccordion) accordion: MatAccordion;
  title = 'Suggestion';
  headers = {
    observe: 'response' as 'response',
    headers: this.reqHeader
  };


  isSelected: Boolean = false;

  flag: Boolean = false;
  english = false;
  hindi = false;
  bengali = false;
  telugu = false;
  sports = false;
  action = false;
  drama = false;
  thriller = false;
  free = false;
  lessThan500 = false;
  bet500And2000 = false;
  above2000 = false;
  dateInput: MatDatepickerInputEvent<Date>;

  recommendList: any[];
  modifiedRecommendList: any[];

  upcomingList: any[];
  modifiedUpcomingList: any[];

  allEventsList: any[];
  modifiedAllEventsList: any[];

  runningList: any = [];
  appliedLanguage: any = [];
  appliedGenre: any = [];
  appliedPrice: any = [];
  city = sessionStorage.getItem('city');
  emailId = localStorage.getItem('email')

  constructor(private http: HttpClient, public datePipe: DatePipe, private router: Router, private snackbar: MatSnackBar, private managementService: ManagementService) {

    //===================================== Recommended events ==============================================

    if (this.city === null && this.emailId != null) {
      console.log("User logged in and no city");
      this.http.get<Event[]>(this.suggestionUrl + '/suggest-events/' + this.emailId, this.headers)
        .subscribe(
          (data) => {
            this.recommendList = data.body;
            this.modifiedRecommendList = data.body;
            console.log("Recommended events in landing page: " + this.recommendList.length);
            for (var i = 0; i < this.modifiedRecommendList.length; i++) {
              if (this.modifiedRecommendList[i].title.length > 10) {
                for (var j = 0; j < 10; j++) {
                  this.modifiedRecommendList[i].title = this.modifiedRecommendList[i].title.substring(0, 10) + "...";
                }
              }
            }
          }
        )
    }
    else if (this.city != null) {
      console.log("User not logged in and city chosen");
      this.http.get<Event[]>(this.suggestionUrl + '/suggestion/' + this.city, this.headers)
        .subscribe(
          (data) => {
            this.recommendList = data.body;
            this.modifiedRecommendList = data.body;
            console.log("City is not blank");
            console.log("Recommended events by city in landing page: " + this.recommendList.length);
            for (var i = 0; i < this.modifiedRecommendList.length; i++) {
              if (this.modifiedRecommendList[i].title.length > 10) {
                for (var j = 0; j < 10; j++) {
                  this.modifiedRecommendList[i].title = this.modifiedRecommendList[i].title.substring(0, 10) + "...";
                }
              }
            }
          }
        )
    }
    else if (this.city == null && this.emailId == null) {
      console.log("User not logged in and no city");
      this.http.get<Event[]>(this.suggestionUrl + '/suggest-events/no-user', this.headers)
        .subscribe(
          (data) => {
            this.recommendList = data.body;
            this.modifiedRecommendList = data.body;
            console.log("City is not blank");
            console.log("Recommended events by city in landing page: " + this.recommendList.length);
            for (var i = 0; i < this.modifiedRecommendList.length; i++) {
              if (this.modifiedRecommendList[i].title.length > 10) {
                for (var j = 0; j < 10; j++) {
                  this.modifiedRecommendList[i].title = this.modifiedRecommendList[i].title.substring(0, 10) + "...";
                }
              }
            }
          }
        )
    }



    //========================================== Upcoming events ===========================================
    this.http.get<Event[]>(this.suggestionUrl + '/upcoming-events', this.headers)
      .subscribe(
        (data) => {
          this.upcomingList = data.body;
          this.modifiedUpcomingList = data.body;
          console.log("Upcoming events in landing page: " + this.upcomingList.length);
          for (var i = 0; i < this.modifiedUpcomingList.length; i++) {
            if (this.modifiedUpcomingList[i].title.length > 10) {
              for (var j = 0; j < 10; j++) {
                this.modifiedUpcomingList[i].title = this.modifiedUpcomingList[i].title.substring(0, 10) + "...";
              }
            }
          }
        }
      )



    //=============================================== fetch all events =======================================
    this.http.get<Event[]>(this.suggestionUrl + '/all-events', this.headers)
      .subscribe(
        (data) => {
          this.modifiedAllEventsList = data.body;
          this.runningList = data.body;
          this.allEventsList = this.runningList;
          console.log("All events in landing page: " + this.runningList.length);
          for (var i = 0; i < this.modifiedAllEventsList.length; i++) {
            if (this.modifiedAllEventsList[i].title.length > 10) {
              for (var j = 0; j < 10; j++) {
                this.modifiedAllEventsList[i].title = this.modifiedAllEventsList[i].title.substring(0, 10) + "...";
              }
            }
          }
        }
      )
    console.log("All events outside subscribe: " + this.allEventsList);

  }


  ngOnInit(): void {
  }


  //user likes an event
  like(eventId: any) {

    if (this.emailId == null) {
      this.router.navigate(['/registration/register']);
    }
    else {
      console.log('in likes() else part');
      this.managementService.updateNoOfLikes(eventId, true).subscribe();//adding 1 like to the event in management service DB 

      this.http.put(this.suggestionUrl + '/update-likes/' + this.emailId + '/' + eventId, this.headers)
        .subscribe(
          (data) => {
            window.location.reload();
          }
        )
    }
  }

  language(language: String) {
    if (language == 'English' && this.english == false) {
      this.appliedLanguage.push('English');
      this.english = true;
    }
    else if (language == 'English' && this.english == true) {
      for (var i = 0; i < this.appliedLanguage.length; i++) {
        if (this.appliedLanguage[i] == 'English') {
          this.appliedLanguage.splice(i, 1);
        }
      }
      this.english = false;
    }
    else if (language == 'Hindi' && this.hindi == false) {
      this.appliedLanguage.push('Hindi');
      this.hindi = true;
    }
    else if (language == 'Hindi' && this.hindi == true) {
      for (var i = 0; i < this.appliedLanguage.length; i++) {
        if (this.appliedLanguage[i] == 'Hindi') {
          this.appliedLanguage.splice(i, 1);
        }
      }
      this.hindi = false;
    }
    else if (language == 'Bengali' && this.bengali == false) {
      this.appliedLanguage.push('Bengali');
      this.bengali = true;
    }
    else if (language == 'Bengali' && this.bengali == true) {
      for (var i = 0; i < this.appliedLanguage.length; i++) {
        if (this.appliedLanguage[i] == 'Bengali') {
          this.appliedLanguage.splice(i, 1);
        }
      }
      this.bengali = false;
    }
    else if (language == 'Telugu' && this.telugu == false) {
      this.appliedLanguage.push('Telugu');
      this.telugu = true;
    }
    else if (language == 'Telugu' && this.telugu == true) {
      for (var i = 0; i < this.appliedLanguage.length; i++) {
        if (this.appliedLanguage[i] == 'Telugu') {
          this.appliedLanguage.splice(i, 1);
        }
      }
      this.telugu = false;
    }
  }

  genre(genre: String) {
    if (genre == 'Sports' && this.sports == false) {
      this.appliedGenre.push('Sports');
      this.sports = true;
    }
    else if (genre == 'Sports' && this.sports == true) {
      for (var i = 0; i < this.appliedGenre.length; i++) {
        if (this.appliedGenre[i] == 'Sports') {
          this.appliedGenre.splice(i, 1);
        }
      }
      this.sports = false;
    }
    if (genre == 'Drama' && this.drama == false) {
      this.appliedGenre.push('Drama');
      this.drama = true;
    }
    else if (genre == 'Drama' && this.drama == true) {
      for (var i = 0; i < this.appliedGenre.length; i++) {
        if (this.appliedGenre[i] == 'Drama') {
          this.appliedGenre.splice(i, 1);
        }
      }
      this.drama = false;
    }
    if (genre == 'Action' && this.action == false) {
      this.appliedGenre.push('Action');
      this.action = true;
    }
    else if (genre == 'Action' && this.action == true) {
      for (var i = 0; i < this.appliedGenre.length; i++) {
        if (this.appliedGenre[i] == 'Action') {
          this.appliedGenre.splice(i, 1);
        }
      }
      this.action = false;
    }
    if (genre == 'Thriller' && this.thriller == false) {
      this.appliedGenre.push('Thriller');
      this.thriller = true;
    }
    else if (genre == 'Thriller' && this.thriller == true) {
      for (var i = 0; i < this.appliedGenre.length; i++) {
        if (this.appliedGenre[i] == 'Thriller') {
          this.appliedGenre.splice(i, 1);
        }
      }
      this.thriller = false;
    }
  }

  price(price: String) {
    if (price == 'free' && this.free == false) {
      this.appliedPrice.push(0);
      this.appliedPrice.push(0);
      this.free = true;
    }
    else if (price == 'free' && this.free == true) {
      this.appliedPrice = [];
      this.free = false;
    }
    if (price == '0-500' && this.lessThan500 == false) {
      this.appliedPrice.push(0);
      this.appliedPrice.push(500);
      this.lessThan500 = true;
    }
    else if (price == '0-500' && this.lessThan500 == true) {
      this.appliedPrice = [];
      this.lessThan500 = false;
    }
    if (price == '500-2000' && this.bet500And2000 == false) {
      this.appliedPrice.push(500);
      this.appliedPrice.push(2000);
      this.bet500And2000 = true;
    }
    else if (price == '500-2000' && this.bet500And2000 == true) {
      this.appliedPrice = [];
      this.bet500And2000 = false;
    }
    if (price == 'above2000' && this.above2000 == false) {
      this.appliedPrice.push(2000);
      this.appliedPrice.push(10000);
      this.above2000 = true;
    }
    else if (price == 'above2000' && this.above2000 == true) {
      this.appliedPrice = [];
      this.above2000 = false;
    }
  }

  apply() {

    this.runningList = [];
    for (var i = 0; i < this.allEventsList.length; i++) {
      var d1 = new Date(this.range.get('start')?.value);
      var d2 = new Date(this.range.get('end')?.value);
      var rangeStartDate = this.datePipe.transform(d1, 'yyyy-MM-dd');
      var rangeEndDate = this.datePipe.transform(d2, 'yyyy-MM-dd');
      var eventStartDate = this.datePipe.transform(this.allEventsList[i].startDate, 'yyyy-MM-dd');
      var eventEndDate = this.datePipe.transform(this.allEventsList[i].endDate, 'yyyy-MM-dd');

      if (((this.ifContains(this.allEventsList[i].languages, this.appliedLanguage)) || this.appliedLanguage.length === 0)
        && ((this.ifContains(this.allEventsList[i].genre, this.appliedGenre)) || this.appliedGenre.length === 0)
        && (
          (
            (rangeStartDate! >= eventStartDate! && rangeEndDate! <= eventEndDate!)
            || (rangeStartDate == '1970-01-01' && rangeEndDate == '1970-01-01')
            || (rangeStartDate! >= eventStartDate! && rangeEndDate == '1970-01-01')
            || (rangeStartDate == '1970-01-01' && rangeEndDate! <= eventEndDate!)
          )
        )
        && (
          (((this.allEventsList[i].price >= this.appliedPrice[0]) && (this.allEventsList[i].price <= this.appliedPrice[1])) || (this.appliedPrice.length === 0))
        )
      ) {
        this.runningList.push(this.allEventsList[i]);
      }


    }
  }

  ifContains(list1: String[], list2: String[]): Boolean {

    var l1: any[] = [];
    var l2: any[] = [];


    for (var i = 0; i < list1.length; i++) {
      l1.push(list1[i].toLowerCase());
    }
    for (var j = 0; j < list2.length; j++) {
      l2.push(list2[j].toLowerCase());
    }
    console.log("l1: " + l1);
    console.log("l2: " + l2);

    var res: boolean = false;
    console.log("list1: " + l1.length + " list2: " + l2.length);
    for (var i = 0; i < l2.length; i++) {
      console.log("list1: " + l1[i]);
      console.log("list2: " + l2[i]);
      if (l1.includes(l2[i])) {
        res = true;
        break;
      }
    }
    console.log("res: " + res);
    return res;
  }


  wishlist(eventId: any, title: string) {
    if (this.emailId == null) {
      this.router.navigate(['/registration/register']);
      console.log("'emaild id null");
    }
    else {
      console.log("'email id not null");
      this.http.put(this.suggestionUrl + '/add-wishlist/' + this.emailId + '/' + eventId, this.headers)
        .subscribe(
          (data) => console.log(data)
        )
      this.snackbar.open(title + ' added into your wishlist', ' ', {
        duration: 3000
      });
    }
  }

}