import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ViewPageService } from '../service/view-page.service';
import { Seat } from '../model/seat';
import { Event } from '../model/event';
import { HttpClient } from '@angular/common/http';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { PriceCategory } from '../model/price-category';
import { Observable } from 'rxjs';

import { CountdownConfig, CountdownEvent } from 'ngx-countdown';
import { TicketingServiceService } from '../service/ticketing-service.service';
import { PaymentService } from '../service/payment.service';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

const KEY = 'time';
const DEFAULT = 100;
declare var Razorpay: any;
@Component({
  selector: 'app-seating-ui',
  templateUrl: './seating-ui.component.html',
  styleUrls: ['./seating-ui.component.css'],
  host: {
    '[class.card]': `true`,
    '[class.text-center]': `true`,
  },
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SeatingUIComponent implements OnInit {
  seats: Seat[] = [];
  seat: Seat = new Seat();
  seatId1: string;
  mouseDown: boolean = false;
  selectedItems: number[] = [];
  selected: Seat[] = [];
  //viewEventurl="http://localhost:5300/ticket";
  paymentDiv = false;
  alphaRows: string[] = [];
  categories: any[];
  config: CountdownConfig = { leftTime: DEFAULT, notify: 0 };
  timex = false;
  pdfhidden=false;
  status: string;
  Name="Me123";
  EmailID="me123@gmail.com"
  seatCategoryOptions: string[] = ['Platinum', 'Gold', 'Silver', 'Common'];
  filteredSeatCategories: Observable<string[]>;
  priceCatogoryList: PriceCategory[] = [];
  colorPalate: string[] = [
    '#fddbe5',
    '#dbfff8',
    '#e0e0ed',
    '#afffa8', 
    '#6495ED',
    '#d1fffa',
    '#DFFF00',
    '#9FE2BF',
  ];
  colorIndexCounter: number = 0;

  constructor(
    private viewEvent: ViewPageService,
    private ActivatedRoute: ActivatedRoute,
    private ticketingService: TicketingServiceService,
    private paymentService: PaymentService
  ) {
    this.email = localStorage.getItem('email');
  }
  eventdetails: Event = new Event();
  url: string;
  email: any;

  display(url: string) {
    console.log(url);

    this.viewEvent.getHostEventforSeats(url).subscribe((result) => {
      this.eventdetails = result;

      this.onDisplay1();
      this.uniquecategories();
      document.documentElement.style.setProperty(
        '--colNum',
        <string>(<unknown>this.eventdetails.seats[1].colm)
      );
    });
  }

  // alphabeticalRow(rows: number) {
  //   this.alphaRows = [];
  //   for (var i = 0; i < rows; i++) {
  //     var w: string = String.fromCharCode(65 + i % 26);
  //     this.alphaRows.push(w);
  //   }
  // }

  uniquecategories() {
    this.categories = this.eventdetails.seats.filter(
      (seats, i, arr) =>
        arr.findIndex((t) => t.seatCategory === seats.seatCategory) === i
    );
    console.log(this.categories);
  }

  onDisplay1() {
    this.eventdetails.seats.forEach((s) => {
      var priceCategory: PriceCategory = new PriceCategory();
      priceCategory.category = s.seatCategory;
      priceCategory.price = s.seatPrice;
      var counter = 0;
      this.priceCatogoryList.forEach((pc) => {
        if (pc.price === s.seatPrice && pc.category === s.seatCategory)
          counter = 1;
      });
      if (counter === 0) this.priceCatogoryList.push(priceCategory);
      counter = 0;
    });

    for (let pcl of this.priceCatogoryList) {
      for (let index = 0; index < this.eventdetails.totalSeats; index++) {
        if (
          pcl.category === this.eventdetails.seats[index].seatCategory &&
          pcl.price === this.eventdetails.seats[index].seatPrice
        ) {
          pcl.divColor = this.colorPalate[this.colorIndexCounter];
        }
      }
      this.colorIndexCounter++;
      if (this.colorIndexCounter >= this.colorPalate.length) {
        this.colorIndexCounter = 0;
      }
    }
  }

  fieldsChange(values: any): void {
    if (values.currentTarget.checked == true) {
      this.selectedItems.push(values.currentTarget.value);
    } else {
      this.selectedItems.splice(
        this.selectedItems.indexOf(values.currentTarget.value),
        1
      );
    }
  }

  getColor(e: any): string {
    for (let pcl of this.priceCatogoryList) {
      if (pcl.category === e.seatCategory) {
        return pcl.divColor;
      }
    }
    return '#FFFFFF';
  }

  holdBooking() {
    const request = new Event();
    var inerval=1000;
    request.seats = [];
    this.selectedItems.forEach((s: number,i) =>
    setTimeout(() => {
      this.ticketingService.getTicket1(this.url, s - 1).subscribe(
        (result) => {
          console.log(this.seat);
          console.log(s - 1);
        },
        (error) => {
          console.log(this.url);
        }
      )
    }, i*1000)
   
    );

    this.showBooking();
  }

  showBooking() {
    this.paymentDiv = true;
    this.showtimer();
  }

  showtimer() {
    this.timex = true;
    let value = +localStorage.getItem(KEY)!! ?? DEFAULT;
    if (value <= 0) value = DEFAULT;
    this.config = { ...this.config, leftTime: value };
    setTimeout(function(){
      window.location.reload();
   }, 100000);
  }

  getTotal(): number {
    let sum = 0;
    this.selectedItems.forEach((item) => {
      // @ts-ignore
      sum += this.eventdetails.seats[item - 1].seatPrice;
    });
    return sum;
  }

  cancel() {
    console.log('Add routing here');
  }

  bookticket() {
    const request = new Event();
    var seatsbooked = 0;
    request.seats = [];
    this.selectedItems.forEach((s: number,i) =>
    setTimeout(() => {
      this.ticketingService.bookseat(this.url, s - 1).subscribe(
        (result) => {
          this.status = result.status;
          console.log(result);
          console.log(s);
          seatsbooked++;
          if(this.selectedItems.length==seatsbooked){
            console.log(this.selectedItems.length);
            console.log(seatsbooked)
            setTimeout(() => {
              this.openPDF();
            }, 2000);
            
          }
        },
        (error) => {
          console.log(error);
          // alert("Ticket not available")
        }
      )  
    }, i*1000),
    );

 
    
  }

  bookeddatadetails(orderId: any, paymentId: any, signature: any) {
    let bookedSeats: any[] = [];
    this.selectedItems.forEach((item) => {
      bookedSeats.push(this.eventdetails.seats[item - 1]);
    });

    var successData = {
      image: this.eventdetails.posters[0],
      host: this.eventdetails.userEmailId,
      email:localStorage.getItem('email'),
      username: localStorage.getItem('name'),
      // email: 'fizhar8@gmail.com',
      eventId: this.eventdetails.eventId,
      amount: this.getTotal(),
      // username: 'hello',
      NoOfSeats:this.selectedItems.length,
      title: this.eventdetails.title,
      description: this.eventdetails.eventDescription,
      venue: this.eventdetails.venue,
      timings: this.eventdetails.eventTimings,
      seats: bookedSeats,
      artists: this.eventdetails.artists,
      orderId: orderId,
      paymentId: paymentId,
      signature: signature,
    };
    this.paymentService.paymentSuccess(successData).subscribe(
      (result) => {
        console.log('Payment Successful, data is transferred');
        console.log(successData)
        this.pdfhidden=true;
        this.bookticket();
      },
      (error) => {
        console.log('Payment successful, data is not transferred');
      }
    );
  }

  pay() {
    let orderId: string;
    let paymentId: string;
    let signature: string;
    let ispaid = false;
    var bookData = {
      email: localStorage.getItem('email'),
      // email: 'abc@gmail.com',
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
refreshpage(){
  window.location.reload();
}
  public openPDF(): void {
    let DATA: any = document.getElementById('htmlData');
    html2canvas(DATA).then((canvas) => {
      let fileWidth = 100;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      const FILEURI = canvas.toDataURL('image/png');
      let PDF = new jsPDF('p', 'mm', 'a4');
      let position = 10;
      
      PDF.addImage(FILEURI, 'PNG', 40, position, fileWidth, fileHeight);
      PDF.save('Carnivryticket.pdf');

      setTimeout(() => {
        this.refreshpage();
      }, 3000);
      
    });
  }

  ngOnInit(): void {
    let id = this.ActivatedRoute.snapshot.paramMap.get('id');
    this.url = id;

    this.display(this.url);

    // this.onDisplay1();
  }

  

  handleEvent(ev: CountdownEvent) {
    if (ev.action === 'notify') {
      // Save current value
      localStorage.setItem(KEY, `${ev.left / 1000}`);
    }
  }
}


