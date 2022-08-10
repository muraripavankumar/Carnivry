// import { Component, OnInit } from '@angular/core';
// import { ActivatedRoute, Router } from '@angular/router';
// import { PaymentService } from '../service/payment.service';
// import { TicketingServiceService } from '../service/ticketing-service.service';
// import { ViewPageService } from '../service/view-page.service';

// @Component({
//   selector: 'app-ticketcomp',
//   templateUrl: './ticketcomp.component.html',
//   styleUrls: ['./ticketcomp.component.css']
// })
// export class TicketcompComponent implements OnInit {

//   constructor(
//     private viewEvent: ViewPageService,
//     private redirect: Router,
//     private ticketingService: TicketingServiceService,
//     private routeUrl: ActivatedRoute,
//     private paymentService: PaymentService 
//   ){

//   }

//   ngOnInit(): void {
//     let id = this.ActivatedRoute.snapshot.paramMap.get('id');
//     this.url = id;

//     this.display(this.url);
//   }

// }
