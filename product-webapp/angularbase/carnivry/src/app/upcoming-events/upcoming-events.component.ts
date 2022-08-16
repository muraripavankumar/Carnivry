import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from '../model/event';
import { Ticket } from '../model/ticket';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-upcoming-events',
  templateUrl: './upcoming-events.component.html',
  styleUrls: ['./upcoming-events.component.css']
})
export class UpcomingEventsComponent implements OnInit {

  upcomingEvents: any;
  email:any;
  noEventMessage:any;

  constructor(private regService:RegistrationService,
     private router: Router) {
    this.email= regService.getEmail();
   }

  ngOnInit(): void {
    console.log(this.email);
   

    this.regService.getUpcomingEvents(this.email).subscribe(res=>{
      this.upcomingEvents= res;
      console.log(this.upcomingEvents);

      if(res.length===0)
         this.noEventMessage= "No Events to Dispaly";
      else
      this.upcomingEvents.map((event:Ticket)=>{
           
        event.timings.startDate= event.timings.startDate.substring(0,10);
        event.timings.endDate= event.timings.endDate.substring(0,10);
       })
    },
    error=>{
      console.error(error);
    })
  }
}
