
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
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
  existingEventData: Event = new Event();
  presentDate: any;

  constructor(private fb: FormBuilder, private managementService: ManagementService, private snackbar: MatSnackBar, private udateEventService: UpdateEventService) {
   
  }

  ngOnInit(): void {
 
  }

  
}
