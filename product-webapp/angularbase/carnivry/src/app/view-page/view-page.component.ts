import { Component, OnInit } from '@angular/core';
import { ViewPageService } from '../service/view-page.service';
import { Event } from '../model/event';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { TicketingServiceService } from '../service/ticketing-service.service';

@Component({
  selector: 'app-view-page',
  templateUrl: './view-page.component.html',
  styleUrls: ['./view-page.component.css']
})
export class ViewPageComponent implements OnInit {

  constructor(private viewEvent:ViewPageService,private redirect: Router,private ticketingService:TicketingServiceService, private routeUrl:ActivatedRoute) { }
  eventdetails:any;
  url:string;
  posterUrl:string;
  paymentDiv=false;

  noOfSeats = new FormControl('');
  selected:number = 0;
  soldout=false;




  display(){
    console.log(this.routeUrl.snapshot.paramMap.get('id'));
    const eventId:string=this.routeUrl.snapshot.paramMap.get('id');
    this.viewEvent.getHostEventById(eventId).subscribe(
      result=>{
        console.log(this.eventdetails)
        this.eventdetails=result;
        
          this.posterUrl=this.eventdetails.posters[1];
  
       
        
      },
      error=>{
        alert("this webpage is not curently available")
        this.redirect.navigate([''])
        console.log(error)
      }
    )
  }

  seatview(id:string){
    if(this.eventdetails.seats[0].colm==0){
this.stream()
    }
    else{
    this.redirect.navigate(['/seat-ui/',id])
    }
  }
  getTotal(): number {
    let sum = 0;

     sum = this.eventdetails.seats[0].seatPrice*this.selected;

    return sum;
  }

  pay(){
    if(this.eventdetails.seats[0].seatId!='0'){
      if(this.eventdetails.ticketsSold<this.eventdetails.totalSeats){
      for(var i=0;i<this.selected;i++){
          this.ticketingService.bookticket(this.eventdetails.eventId).subscribe(
            result=>{
              console.log(this.eventdetails.ticketsSold)
            },
            error=>{
              alert("Tickets are sold out")
            }
          );    
      }
    }
    else{
      this.soldoutTix();
    }
  }
else{
  console.log("Magnus Carlsen")
  for(var i=0;i<this.selected;i++){
    this.ticketingService.streamingBooking(this.eventdetails.eventId).subscribe(
      result=>{
        console.log(this.eventdetails.ticketsSold)
      },
      error=>{
        alert("We are unavailable")
      }
    );    
}
}

  }


  stream(){
    this.paymentDiv=true;
  }



  soldoutTix(){
    this.soldout=true;
    this.paymentDiv=false;
  }
  ngOnInit(): void {
this.display()
    this.url=this.eventdetails.eventId;
  }

}
