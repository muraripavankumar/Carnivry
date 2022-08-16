import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from '../model/event';
import { Ticket } from '../model/ticket';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-past-events',
  templateUrl: './past-events.component.html',
  styleUrls: ['./past-events.component.css']
})
export class PastEventsComponent implements OnInit {

  pastEvents: any;
  email:any;
  noEventMessage:any;
  isSeat:boolean= false;

  constructor(private regService:RegistrationService,
     private router: Router) {
    this.email= regService.getEmail();
   }

  ngOnInit(): void {
    console.log(this.email);
   

    this.regService.getPastEvents(this.email).subscribe(res=>{
      this.pastEvents= res;
      console.log(this.pastEvents);

      if(res.length===0)
         this.noEventMessage= "No Events to Dispaly";
      else
      this.pastEvents.map((event:Ticket)=>{
        console.log(event.seats);   
        event.timings.startDate= event.timings.startDate.substring(0,10);
        event.timings.endDate= event.timings.endDate.substring(0,10);
       })
    },
    error=>{
      console.error(error);
    })
  }


}
