import { Component, OnInit } from '@angular/core';
import { ViewPageService } from '../service/view-page.service';
import { Event } from '../model/event';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { TicketingServiceService } from '../service/ticketing-service.service';
import { PaymentService } from '../service/payment.service';
import { Seat } from '../model/seat';
declare var Razorpay: any;


@Component({
  selector: 'app-view-page',
  templateUrl: './view-page.component.html',
  styleUrls: ['./view-page.component.css'],
})
export class ViewPageComponent implements OnInit {
  constructor(
    private viewEvent: ViewPageService,
    private redirect: Router,
    private ticketingService: TicketingServiceService,
    private routeUrl: ActivatedRoute,
    private paymentService: PaymentService 
  ) {}
  eventdetails: any;
  url: string;
  posterUrl: string;
  thumbnailurl:string;
  paymentDiv = false;
  seats: Seat[] = [];
  noOfSeats = new FormControl('');
  selected: number = 0;
  soldout = false;

  
  display() {
    console.log(this.routeUrl.snapshot.paramMap.get('id'));
    const eventId: string = this.routeUrl.snapshot.paramMap.get('id');
    this.viewEvent.getHostEventById(eventId).subscribe(
      (result) => {
        console.log(this.eventdetails);
        this.eventdetails = result;

        this.posterUrl = this.eventdetails.posters[1];
        this.thumbnailurl= this.eventdetails.posters[0];
        // document.getElementById('background-image').style.backgroundImage = "url('" + this.posterUrl + "')"
      },
      (error) => {
        alert('this webpage is not curently available');
        this.redirect.navigate(['']);
        console.log(error);
      }
    );
  }

  seatview(id: string) {
    if (this.eventdetails.seats[0].colm == 0) {
      this.stream();
    } else {
      this.redirect.navigate(['/seat-ui/', id]);
    }
  }
  getTotal(): number {
    let sum = 0;

    sum = this.eventdetails.seats[0].seatPrice * this.selected;

    return sum;
  }

  pay() {
    if (this.eventdetails.seats[0].seatId != '0') {
      if (this.eventdetails.ticketsSold < this.eventdetails.totalSeats) {
        for (var i = 0; i < this.selected; i++) {
          this.ticketingService.bookticket(this.eventdetails.eventId).subscribe(
            (result) => {
              console.log(this.eventdetails.ticketsSold);
            },
            (error) => {
              alert('Tickets are sold out');
            }
          );
        }
      } else {
        this.soldoutTix();
      }
    } else {
      console.log('Magnus Carlsen');
      for (var i = 0; i < this.selected; i++) {
        this.ticketingService
          .streamingBooking(this.eventdetails.eventId)
          .subscribe(
            (result) => {
              console.log(this.eventdetails.ticketsSold);
            },
            (error) => {
              alert('We are unavailable');
            }
          );
      }
    }
  }

  stream() {
    this.paymentDiv = true;
  }

  soldoutTix() {
    this.soldout = true;
    this.paymentDiv = false;
  }

  bookeddatadetails(orderId: any, paymentId: any, signature: any) {
   

    var successData = {
      image: this.eventdetails.posters[0],
      host: this.eventdetails.userEmailId,
      email: 'abc@gmail.com',
      eventId: this.eventdetails.eventId,
      NoOfSeats:this.selected,
      amount: this.getTotal(),
      username: 'hello1234',
      title: this.eventdetails.title,
      description: this.eventdetails.eventDescription,
      venue: this.eventdetails.venue,
      timings: this.eventdetails.eventTimings,
      seats: [this.eventdetails.seats[0]],
      artists: this.eventdetails.artists,
      orderId: orderId,
      paymentId: paymentId,
      signature: signature,
    };
    console.log(successData)
    this.paymentService.paymentSuccess(successData).subscribe(
      (result) => {
        console.log('Payment Successful, data is transferred');
       this.pay();
        console.log(successData)
      },
      (error) => {
        console.log(error)
        console.log('Payment successful, data is not transferred');
      }
    );
  }

  payment() {
    let orderId: string;
    let paymentId: string;
    let signature: string;
    let ispaid = false;
    var bookData = {
      email: 'abc@gmail.com',
      eventId: this.eventdetails.eventId,
      amount: this.getTotal(),
    };

    this.paymentService.createOrder(bookData).subscribe(
      (res) => {
        console.log('Order created');
        console.log(res);
        if (res.status === 'created') {
          console.log(true);

          var options = {
            key: 'rzp_test_Sxqcnn9dko0BzB', // Enter the Key ID generated from the Dashboard
            amount: res.amount, // Amount is in currency subunits. Default currency is

            currency: res.currency,
            name: 'Carnivry',
            description: 'Carnivry Ticket Booking',
            image:
              'https://www.digitaloutlook.com.au/wp-content/uploads/2017/09/future_payment_methods-compressor-1.jpg',
            order_id: res.id, //This is a sample Order ID.
            handler: function (res: {
              razorpay_payment_id: any;
              razorpay_order_id: any;
              razorpay_signature: any;
            }) {
              console.log(res.razorpay_payment_id);
              console.log(res.razorpay_order_id);
              console.log(res.razorpay_signature);
              console.log('Payment Successful');
              orderId = res.razorpay_order_id;
              paymentId = res.razorpay_payment_id;
              signature = res.razorpay_signature;
              ispaid = true;
            },
            prefill: {
              name: 'Carnivryuser1',
              email: bookData.email,
              contact: '111111111',
            },
            notes: {
              address: 'Carnivry Pvt. Ltd.',
            },
            theme: {
              color: '#3399cc',
            },
          };

          // var rzp1 = new Razorpay(options);
          var rzp1 = new Razorpay(options);
          rzp1.on(
            'payment.failed',
            function (response: {
              error: {
                code: any;
                description: any;
                source: any;
                step: any;
                reason: any;
                metadata: { order_id: any; payment_id: any };
              };
            }) {
              console.log(response.error.code);
              console.log(response.error.description);
              console.log(response.error.source);
              console.log(response.error.step);
              console.log(response.error.reason);
              console.log(response.error.metadata.order_id);
              console.log(response.error.metadata.payment_id);

              alert('Sorry! Payment failed.');
            }
          );

          rzp1.open();
          let check = setInterval(() => {
            if (ispaid === true) {
             
              this.bookeddatadetails(res.id, paymentId, signature);
              
              clearInterval(check);
            }
          }, 10000);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  ngOnInit(): void {
    this.display();
    this.url = this.routeUrl.snapshot.paramMap.get('id');
  }
}
