
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from '../model/event';
import { ManagementService } from '../service/management.service';
import { UpdateEventService } from '../service/update-event.service';

@Component({
  selector: 'app-update-event',
  templateUrl: './update-event.component.html',
  styleUrls: ['./update-event.component.css']
})
export class UpdateEventComponent implements OnInit {

  eventData: Event;
  allEvents: Event[];
  existingEventData: Event = new Event();
  presentDate: any;

  constructor(private managementService: ManagementService, private udateEventService: UpdateEventService,private router:Router) {
  }

  ngOnInit(): void {
    const userEmailId:string="wantobeanonymous8@gmail.com";
    this.managementService.getAllEventsByUserEmailId(userEmailId).subscribe((data) => this.allEvents = data);
  }
  updateEvent(eventObj: Event) {
    this.udateEventService.updateEventInit(eventObj);
    this.router.navigate(['/host-event']);
  }


}
