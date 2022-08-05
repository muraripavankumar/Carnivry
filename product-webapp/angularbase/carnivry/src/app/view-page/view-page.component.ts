import { Component, OnInit } from '@angular/core';
import { ViewPageService } from '../service/view-page.service';
import { Event } from '../model/event';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-page',
  templateUrl: './view-page.component.html',
  styleUrls: ['./view-page.component.css']
})
export class ViewPageComponent implements OnInit {

  constructor(private viewEvent:ViewPageService,private redirect: Router) { }
  eventdetails:any;
  url:String;

  display(){
    this.viewEvent.getHostEventById().subscribe(
      result=>{
        console.log(this.eventdetails)
        this.eventdetails=result;
      }
    )
  }

  seatview(id:any){
    this.redirect.navigate(['/seat-ui/',id])
  }

  ngOnInit(): void {
this.display()
    this.url=this.eventdetails.eventId;
  }

}
