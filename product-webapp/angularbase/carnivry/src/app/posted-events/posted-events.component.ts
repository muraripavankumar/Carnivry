import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationService } from '../service/registration.service';
import { Event } from '../model/event';
import { T } from '@angular/cdk/keycodes';
import { UpdateEventService } from '../service/update-event.service';
import { ManagementService } from '../service/management.service';

@Component({
  selector: 'app-posted-events',
  templateUrl: './posted-events.component.html',
  styleUrls: ['./posted-events.component.css']
})
export class PostedEventsComponent implements OnInit {

  postedEvents: any;
  email:any

  constructor(private regService:RegistrationService,
     private router: Router,
     private updateEventService:UpdateEventService,
     private managementService: ManagementService) {
    this.email= regService.getEmail();
   }

  ngOnInit(): void {
    console.log(this.email);
   

    this.managementService.getAllEventsByUserEmailId(this.email).subscribe(res=>{
      this.postedEvents= res;
      console.log(this.postedEvents);

      this.postedEvents.map((event:Event)=>{
           
        event.eventTimings.startDate= event.eventTimings.startDate.substring(0,10);
        event.eventTimings.endDate= event.eventTimings.endDate.substring(0,10);
       })
    },
    error=>{
      console.error(error);
    })
  }

  view(e:Event){
    this.updateEventService.updateEventInit(e);
    this.router.navigate(['/host-event']);
  }

}
