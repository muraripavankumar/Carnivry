import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Event } from '../model/event';
import { ManagementService } from './management.service';

@Injectable({
  providedIn: 'root'
})
export class UpdateEventService {
  private objectSource = new BehaviorSubject<Event>(new Event);
  private eventObj: Event;
  obj = this.objectSource.asObservable();

  updationEvent: Event;

  constructor(private managementService: ManagementService) { }

  updateEventInit(event: Event) {
    this.objectSource.next(event);
  }
  // updateEventCall(eventId: string) {
  //   this.managementService.getHostEventById(eventId).subscribe((data) => this.eventObj = data);
  //   this.objectSource.next(this.eventObj);
  // }
}
