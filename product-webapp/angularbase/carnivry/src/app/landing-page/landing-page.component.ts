import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MatAccordion } from '@angular/material/expansion';
import { tap } from 'rxjs';

interface carouselImage{
  imageSrc: string;
  imageAlt: string;
}

interface City {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  cities: City[] = [
    {value: 'jodhpur', viewValue: 'Jodhpur'},
    {value: 'mumbai', viewValue: 'Mumbai'},
    {value: 'delhi', viewValue: 'Delhi'},
    {value: 'bengaluru', viewValue: 'Bengaluru'},
    {value: 'hyderabad', viewValue: 'Hyderabad'},
    {value: 'ahmedabad', viewValue: 'Ahmedabad'},
    {value: 'chandigarh', viewValue: 'Chandigarh'},
    {value: 'chennai', viewValue: 'Chennai'},
    {value: 'pune', viewValue: 'Pune'},
    {value: 'kolkata', viewValue: 'Kolkata'}
  ];
  cityControl = new FormControl('None');
  form = new FormGroup({
    city: this.cityControl,
  });

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
  headers={
    observe: 'response' as 'response'
};


flag: Boolean=false;
english=false;
hindi= false;
bengali=false;
telugu=false;
sports=false;
action=false;
drama=false;
thriller= false;
free=false;
lessThan500=false;
bet500And2000=false;
above2000=false;
dateInput: MatDatepickerInputEvent<Date>;

recommendData: any;
recommendList: any;
recommendPosterList: any;

upComingData: any;
upcomingList: any;
upcomingPosterList: any;

allEventsData: any;
allEventsList: any;
allEventsPosterList: any;

runningList: any=[];
appliedLanguage: any=[];
appliedGenre: any=[];
appliedPrice: any=[];

constructor(private http: HttpClient, public datePipe: DatePipe){
    
  // Recommended events 

  console.log("City value: "+sessionStorage.getItem('city'));
  
  
console.log("No city selected");
  this.http.get('http://localhost:8082/api/v1/suggest-events/gm@gmail.com', this.headers)
  .pipe(
    tap(res=>{ sessionStorage.setItem("recom",JSON.stringify(res.body))
        console.log(res.body);
        }
      )
    )
  .subscribe(
    {
      next: (response)=>console.log(response),
      error: (error)=>console.log(error),
    }
  )

  this.recommendData=[];
  this.recommendPosterList=[];
  this.recommendList=[];
  this.recommendData = sessionStorage.getItem("recom");
  
  var recommendedJSON: any = this.recommendData!==null ? JSON.parse(this.recommendData): this.recommendData;

  
  for(var i=0; i<recommendedJSON?.length; i++){
    this.recommendPosterList.push(recommendedJSON[i]);
  }

  this.recommendList=this.recommendPosterList;
  console.log("Recommended length: "+this.recommendPosterList.length);



  // Upcoming events
  this.http.get('http://localhost:8082/api/v1/upcoming-events/gm@gmail.com', this.headers)
  .pipe(
    tap(res=>{sessionStorage.setItem("upcom",JSON.stringify(res.body))
        console.log(res.body);
        }
      )
    )
  .subscribe(
    {
      next: (response)=>console.log(response),
      error: (error)=>console.log(error)
    }
  )

  this.upComingData=[];
  this.upcomingPosterList=[];
  this.upcomingList=[];
  this.upComingData = sessionStorage.getItem("upcom");
  
  var upcomingJSON: any = this.upComingData!==null ? JSON.parse(this.upComingData): this.upComingData;

  
  for(var i=0; i<upcomingJSON?.length; i++){
    this.upcomingPosterList.push(upcomingJSON[i]);
  }

  this.upcomingList=this.upcomingPosterList;

  
  // fetch all events
  this.http.get('http://localhost:8082/api/v1/all-events', this.headers)
  .pipe(
    tap(res=>{sessionStorage.setItem("allshows",JSON.stringify(res.body))
        console.log(res.body);
        }
      )
    )
  .subscribe(
    {
      next: (response)=>console.log(response),
      error: (error)=>console.log(error)
    }
  )

  this.allEventsData=[];
  this.allEventsPosterList=[];
  this.allEventsList=[];
  this.allEventsData = sessionStorage.getItem("allshows");
  
  var allEventsJSON: any = this.allEventsData!==null ? JSON.parse(this.allEventsData): this.allEventsData;

  for(var i=0; i<allEventsJSON?.length; i++){
    this.allEventsPosterList.push(allEventsJSON[i]);
  }

  this.allEventsList=this.allEventsPosterList;
  this.runningList=this.allEventsList;
  console.log("All Events length: "+this.allEventsList.length);

}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }


 //user likes an event
 like(eventId:any){
  this.http.put('http://localhost:8082/api/v1/update-likes/gm@gmail.com/'+eventId, this.headers)
  .pipe(
    tap(res=>{sessionStorage.setItem("like",JSON.stringify(res))
        console.log(res);
        }
      )
    )
  .subscribe(
    {
      next: (response)=>console.log(response),
      error: (error)=>console.log(error)
    }
  )}

language(language:String){
  if(language=='English' && this.english==false){
    this.appliedLanguage.push('English');
    this.english=true;
  }
  else if(language=='English' && this.english==true){
    for(var i=0; i<this.appliedLanguage.length; i++){
      if(this.appliedLanguage[i]=='English'){
        this.appliedLanguage.splice(i,1);
      }
    }
    this.english=false;
  }
  else if(language=='Hindi' && this.hindi==false){
    this.appliedLanguage.push('Hindi');
    this.hindi=true;
  }
  else if(language=='Hindi' && this.hindi==true){
    for(var i=0; i<this.appliedLanguage.length; i++){
      if(this.appliedLanguage[i]=='Hindi'){
        this.appliedLanguage.splice(i,1);
      }
    }
    this.hindi=false;
  }
  else if(language=='Bengali' && this.bengali==false){
    this.appliedLanguage.push('Bengali');
    this.bengali=true;
  }
  else if(language=='Bengali' && this.bengali==true){
    for(var i=0; i<this.appliedLanguage.length; i++){
      if(this.appliedLanguage[i]=='Bengali'){
        this.appliedLanguage.splice(i,1);
      }
    }
    this.bengali=false;
  }
  else if(language=='Telugu' && this.telugu==false){
    this.appliedLanguage.push('Telugu');
    this.telugu=true;
  }
  else if(language=='Telugu' && this.telugu==true){
    for(var i=0; i<this.appliedLanguage.length; i++){
      if(this.appliedLanguage[i]=='Telugu'){
        this.appliedLanguage.splice(i,1);
      }
    }
    this.telugu=false;
  }
}

genre(genre:String){
  if(genre=='Sports' && this.sports==false){
    this.appliedGenre.push('Sports');
    this.sports=true;
  }
  else if(genre=='Sports' && this.sports==true){
    for(var i=0; i<this.appliedGenre.length; i++){
      if(this.appliedGenre[i]=='Sports'){
        this.appliedGenre.splice(i,1);
      }
    }
    this.sports=false;
  }
  if(genre=='Drama' && this.drama==false){
    this.appliedGenre.push('Drama');
    this.drama=true;
  }
  else if(genre=='Drama' && this.drama==true){
    for(var i=0; i<this.appliedGenre.length; i++){
      if(this.appliedGenre[i]=='Drama'){
        this.appliedGenre.splice(i,1);
      }
    }
    this.drama=false;
  }
  if(genre=='Action' && this.action==false){
    this.appliedGenre.push('Action');
    this.action=true;
  }
  else if(genre=='Action' && this.action==true){
    for(var i=0; i<this.appliedGenre.length; i++){
      if(this.appliedGenre[i]=='Action'){
        this.appliedGenre.splice(i,1);
      }
    }
    this.action=false;
  }
  if(genre=='Thriller' && this.thriller==false){
    this.appliedGenre.push('Thriller');
    this.thriller=true;
  }
  else if(genre=='Thriller' && this.thriller==true){
    for(var i=0; i<this.appliedGenre.length; i++){
      if(this.appliedGenre[i]=='Thriller'){
        this.appliedGenre.splice(i,1);
      }
    }
    this.thriller=false;
  }
}

price(price: String){
  if(price=='free' && this.free==false){
    this.appliedPrice.push(0);
    this.appliedPrice.push(0);
    this.free=true;
  }
  else if(price=='free' && this.free==true){
    this.appliedPrice=[];
    this.free=false;
  }
  if(price=='0-500' && this.lessThan500==false){
    this.appliedPrice.push(0);
    this.appliedPrice.push(500);
    this.lessThan500=true;
  }
  else if(price=='0-500' && this.lessThan500==true){
    this.appliedPrice=[];
    this.lessThan500=false;
  }
  if(price=='500-2000' && this.bet500And2000==false){
    this.appliedPrice.push(500);
    this.appliedPrice.push(2000);
    this.bet500And2000=true;
  }
  else if(price=='500-2000' && this.bet500And2000==true){
    this.appliedPrice=[];
    this.bet500And2000=false;
  }
  if(price=='above2000' && this.above2000==false){
    this.appliedPrice.push(2000);
    this.appliedPrice.push(10000);
    this.above2000=true;
  }
  else if(price=='above2000' && this.above2000==true){
    this.appliedPrice=[];
    this.above2000=false;
  }
}

apply(){

  this.runningList=[];
  for(var i=0; i<this.allEventsList.length; i++){
    var d1= new Date(this.range.get('start')?.value);
    var d2= new Date(this.range.get('end')?.value);
    var rangeStartDate= this.datePipe.transform(d1,'yyyy-MM-dd');
    var rangeEndDate= this.datePipe.transform(d2,'yyyy-MM-dd');
    var eventStartDate= this.datePipe.transform(this.allEventsList[i].startDate, 'yyyy-MM-dd');
    var eventEndDate= this.datePipe.transform(this.allEventsList[i].endDate, 'yyyy-MM-dd');

    if( ( (this.ifContains(this.allEventsList[i].languages, this.appliedLanguage)) || this.appliedLanguage.length===0) 
        && ( (this.ifContains(this.allEventsList[i].genre, this.appliedGenre)) || this.appliedGenre.length===0)
        && (
            (
              (rangeStartDate!>=eventStartDate! && rangeEndDate!<=eventEndDate!) 
              || (rangeStartDate=='1970-01-01' && rangeEndDate=='1970-01-01') 
              || (rangeStartDate!>=eventStartDate! && rangeEndDate=='1970-01-01') 
              || (rangeStartDate=='1970-01-01' && rangeEndDate!<=eventEndDate!)
            )
          )
        && (
            (((this.allEventsList[i].price>=this.appliedPrice[0]) && (this.allEventsList[i].price<=this.appliedPrice[1])) || (this.appliedPrice.length===0))
        ) 
      )
    {
      this.runningList.push(this.allEventsList[i]);
    }
  }
}

ifContains(list1:String[], list2:String[]): Boolean{
  var res: boolean=false;
  console.log("list1: "+list1.length+ " list2: "+list2.length);
  for(var i=0; i<list2.length; i++){
    console.log("list1: "+list1[i]);
    console.log("list2: "+list2[i]);
    if(list1.includes(list2[i])){
      res=true;
      break;
    }
  }
  console.log("res: "+res);
  return res;
}

chooseCity(city: any){
  this.form = new FormGroup({
    city: this.cityControl,
  });
  
  console.log("You selected city: "+ this.form.get('city')?.value);

  console.log("Selected city: "+city);
  this.http.get('http://localhost:8082/api/v1/suggestion/'+this.form.get('city')?.value, this.headers)
  // .pipe(
  //   tap(res=>{ sessionStorage.setItem("Ecity",JSON.stringify(res.body))
  //       console.log(res.body);
  //       this.recommendData=res.body;
  //       }
  //     )
  //   )
  .subscribe( (res=> {
    this.recommendData=res.body;
  })
    // {
    //   next: (response)=>console.log(response),
    //   error: (error)=>console.log(error),
    // }

  )

  this.recommendData=[];
  this.recommendPosterList=[];
  this.recommendList=[];
  this.recommendData = sessionStorage.getItem("Ecity");
  // this.recommendList.push(this.recommendData);
  
  var recommendedJSON: any = this.recommendData!==null ? JSON.parse(this.recommendData): this.recommendData;

  
  for(var i=0; i<recommendedJSON?.length; i++){
    this.recommendPosterList.push(recommendedJSON[i]);
  }

  this.recommendList=this.recommendPosterList;
  console.log("Recommended length: "+this.recommendPosterList.length);
}

}
