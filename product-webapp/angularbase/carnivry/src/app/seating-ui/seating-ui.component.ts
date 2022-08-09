import { ChangeDetectionStrategy,Component, OnInit } from '@angular/core';
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
import { stringToKeyValue } from '@angular/flex-layout/extended/style/style-transforms';
import { RazorpaySuccess } from '../model/razorpay-success';


const KEY = 'time';
const DEFAULT = 20;
declare var Razorpay:any;
@Component({
  selector: 'app-seating-ui',
  templateUrl: './seating-ui.component.html',
  styleUrls: ['./seating-ui.component.css'],
  host: {
    '[class.card]': `true`,
    '[class.text-center]': `true`,
  },
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SeatingUIComponent implements OnInit {
 seats: Seat[] = [];
 seat: Seat = new Seat();
 seatId1:string;
  mouseDown: boolean = false;
  selectedItems: number[] = [];
  selected: Seat[]=[];
  viewEventurl="http://localhost:5300/ticket";
  paymentDiv = false;
  alphaRows: string[] = [];
  categories: any[];
  config: CountdownConfig = { leftTime: DEFAULT, notify: 0 };
  timex = false;
  
  
  seatCategoryOptions: string[] = ['Platinum', 'Gold', 'Silver', 'Common'];
  filteredSeatCategories: Observable<string[]>;
  priceCatogoryList: PriceCategory[] = [];
  colorPalate: string[] = ['#fddbe5','#dbfff8', '#dcffd9', '#e0e0ed', '#6495ED', '#d1fffa',  '#DFFF00', '#9FE2BF'];
  colorIndexCounter: number = 0;

  constructor(private viewEvent:ViewPageService,private httpClient:HttpClient,
    private location: Location,private ActivatedRoute: ActivatedRoute,private ticketingService: TicketingServiceService,private paymentService:PaymentService) { 
      this.email=localStorage.getItem('email')
    }
  eventdetails:Event=new Event();
  url:string;
  email:any;


  display(url:string){
    console.log(url)
    
    this.viewEvent.getHostEventforSeats(url).subscribe(
      result=>{
       
        this.eventdetails=result;
        
        this.onDisplay1();
        this.uniquecategories();
        document.documentElement.style.setProperty("--colNum",<string><unknown>this.eventdetails.seats[1].colm)
      }
     
    )
  }

  
  // alphabeticalRow(rows: number) {
  //   this.alphaRows = [];
  //   for (var i = 0; i < rows; i++) {
  //     var w: string = String.fromCharCode(65 + i % 26);
  //     this.alphaRows.push(w);
  //   }
  // }

  uniquecategories(){
      this.categories=this.eventdetails.seats.filter((seats,i,arr)=> arr.findIndex(t=>t.seatCategory===seats.seatCategory)===i);
      console.log(this.categories);
  }


onDisplay1(){
  this.eventdetails.seats.forEach((s) => {
    var priceCategory: PriceCategory = new PriceCategory();
    priceCategory.category = s.seatCategory;
    priceCategory.price = s.seatPrice;
    var counter = 0;
    this.priceCatogoryList.forEach((pc) => {
      if (pc.price === s.seatPrice && pc.category === s.seatCategory)
        counter = 1;
    });
    if (counter === 0)
    this.priceCatogoryList.push(priceCategory);
  counter = 0;
  });

  for (let pcl of this.priceCatogoryList) {
    for (let index = 0; index < this.eventdetails.totalSeats; index++) {
      if (pcl.category === this.eventdetails.seats[index].seatCategory && pcl.price === this.eventdetails.seats[index].seatPrice) {
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
    }
    else {
      this.selectedItems.splice(this.selectedItems.indexOf(values.currentTarget.value), 1);
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

  holdBooking(){
    const request = new Event();
    request.seats = [];
    this.selectedItems.forEach((s:number)=>this.ticketingService.getTicket1(this.url,s-1).subscribe(
  result=>{
    console.log(this.seat);
    console.log(s-1);
  },
  error=>{
    console.log(this.url)
    alert("Ticket not available")
  }
))

this.showBooking();
    }

    showBooking(){
        this.paymentDiv=true;
        this.showtimer();
    }

    showtimer(){
      this.timex=true;
      let value = +localStorage.getItem(KEY)!! ?? DEFAULT;
if (value <= 0) value = DEFAULT;
this.config = { ...this.config, leftTime: value };

    }

    getTotal(): number {
      let sum = 0;
      this.selectedItems.forEach(item => {
        // @ts-ignore
        sum += this.eventdetails.seats[item-1].seatPrice;
      });
      return sum;
    }

    // getTicket1(s:number){
    //   return this.httpClient.get<any>(this.viewEventurl+"/" + this.url+"/"+s)
    // }
  
    cancel(){
      console.log("Add routing here")
      
    }

    

    // confirm(){
    //   let bookedSeats: any[]= [];
    //   this.selectedItems.forEach((item)=>{
    //     bookedSeats.push(this.eventdetails.seats[item-1]);
    //   })



    //   var successData= {
    //         "email" :"abc@gmail.com",
    //         "eventId": this.eventdetails.eventId,
    //         "amount": this.getTotal(),
    //         "title": this.eventdetails.title,
    //         "description": this.eventdetails.eventDescription,
    //         "venue": this.eventdetails.venue,
    //         "timings": this.eventdetails.eventTimings,
    //         "seats": bookedSeats,
    //         "username": "teo234",
            
            
    //       }
    //     console.log(successData);
        
    // }

    bookeddatadetails(orderId:any,paymentId:any,signature:any ){
      let bookedSeats: any[]= [];
      this.selectedItems.forEach((item)=>{
        bookedSeats.push(this.eventdetails.seats[item-1]);
      })



      var successData= {
            "image":this.eventdetails.posters[0],
            "host" : this.eventdetails.userEmailId,
            "email" :"abc@gmail.com",
            "eventId": this.eventdetails.eventId,
            "amount": this.getTotal(),
            "title": this.eventdetails.title,
            "description": this.eventdetails.eventDescription,
            "venue": this.eventdetails.venue,
            "timings": this.eventdetails.eventTimings,
            "seats": bookedSeats,
            "username": "teo234",
            "orderId" : orderId,
            "paymentId" : paymentId,
            "signature" : signature
            
            
          }
          this.paymentService.paymentSuccess(successData).subscribe(
            result=>{
              console.log("Payment Successful, data is transferred")
            },
            error=>{
              console.log("Payment successful, data is not transferred")
            }
          )
        

    }

    
    
    pay(){
      let orderId: string;
      let paymentId: string;
      let signature: string;
      let ispaid=false;
        var bookData={
          "email" :"abc@gmail.com",
          "eventId": this.eventdetails.eventId,
          "amount": this.getTotal()
        }


        this.paymentService.createOrder(bookData).subscribe(res=>{
      
          console.log("Order created");
          console.log(res);
          if(res.status==='created')
           {
            console.log(true);
            
            var options = {

              
              "key": "rzp_test_Sxqcnn9dko0BzB", // Enter the Key ID generated from the Dashboard
              "amount": res.amount, // Amount is in currency subunits. Default currency is
              
              "currency": res.currency,
              "name": "Carnivry",
              "description": "Carnivry Ticket Booking",
              "image": "https://www.digitaloutlook.com.au/wp-content/uploads/2017/09/future_payment_methods-compressor-1.jpg",
              "order_id": res.id, //This is a sample Order ID.
              "handler": function (res: { razorpay_payment_id: any; razorpay_order_id: any; razorpay_signature: any; }){
              console.log(res.razorpay_payment_id);
              console.log(res.razorpay_order_id);
              console.log(res.razorpay_signature);
              console.log("Payment Successful");
              orderId=res.razorpay_order_id;
              paymentId=res.razorpay_payment_id;
              signature=res.razorpay_signature;
              ispaid=true;

            
            
              },
              "prefill": {
              "name": "Carnivryuser1",
              "email": bookData.email,
              "contact": "111111111"
              },
              "notes": {
              "address": "Carnivry Pvt. Ltd."
              
              },
              "theme": {
              "color": "#3399cc"
              },
              
              };
    
              // var rzp1 = new Razorpay(options);
              var rzp1 = new Razorpay(options);
              rzp1.on('payment.failed', function (response: { error: { code: any; description: any; source: any; step: any; reason: any; metadata: { order_id: any; payment_id: any; }; }; }){
              console.log(response.error.code);
              console.log(response.error.description);
              console.log(response.error.source);
              console.log(response.error.step);
              console.log(response.error.reason);
              console.log(response.error.metadata.order_id);
              console.log(response.error.metadata.payment_id);
    
              alert("Sorry! Payment failed.");
              });
              
              rzp1.open();
             let check= setInterval(() => {
                if(ispaid===true){
                  this.bookeddatadetails(orderId,paymentId,signature);
                  clearInterval(check)
                }
              }, 10000);
             
    
             
           }
          
           
        },
        error=>{
          console.log(error);
        });

      }


  


  ngOnInit(): void {
    let id = this.ActivatedRoute.snapshot.paramMap.get('id');
this.url=id;

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

