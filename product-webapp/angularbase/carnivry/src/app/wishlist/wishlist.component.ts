import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from '../model/event';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  wishlist: any;
  email:any;
  noEventMessage:any;

  constructor(private regService:RegistrationService,
     private router: Router) {
    this.email= regService.getEmail();
   }

  ngOnInit(): void {
    console.log(this.email);
   

    this.regService.getWishlist(this.email).subscribe(res=>{
      this.wishlist= res;
      console.log(this.wishlist);

      if(res===null)
         this.noEventMessage= "No Events to Dispaly";
      else
      this.wishlist.map((event:Event)=>{
           
        event.eventTimings.startDate= event.eventTimings.startDate.substring(0,10);
        event.eventTimings.endDate= event.eventTimings.endDate.substring(0,10);
       })
    },
    error=>{
      console.error(error);
    })
  }

}
