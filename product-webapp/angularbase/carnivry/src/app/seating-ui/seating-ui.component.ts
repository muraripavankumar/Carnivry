import { Component, OnInit } from '@angular/core';
import { ViewPageService } from '../service/view-page.service';
import { Seat } from '../model/seat';
import { Event } from '../model/event';
import { HttpClient } from '@angular/common/http';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-seating-ui',
  templateUrl: './seating-ui.component.html',
  styleUrls: ['./seating-ui.component.css']
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

  constructor(private viewEvent:ViewPageService,private httpClient:HttpClient,private location: Location,private ActivatedRoute: ActivatedRoute) { }
  eventdetails:any;
  url:any;
  display(url:any){
    this.viewEvent.getHostEventforSeats(url).subscribe(
      result=>{
        console.log(this.eventdetails)
        this.eventdetails=result;
      }
    )
  }


  fieldsChange(values: any): void {
    if (values.currentTarget.checked == true) {
      this.selectedItems.push(values.currentTarget.value);
    }
    else {
      this.selectedItems.splice(this.selectedItems.indexOf(values.currentTarget.value), 1);
    }
  }

  // checkBox(values: any) {
  //   let box = (document.getElementById(values.currentTarget.id) as HTMLInputElement).checked;
  //   if (this.mouseDown) {
  //     if (box) {
  //       (document.getElementById(values.currentTarget.id) as HTMLInputElement).checked = false;
  //       this.fieldsChange(values);
  //     }
  //     else {
  //       (document.getElementById(values.currentTarget.id) as HTMLInputElement).checked = true;
  //       this.fieldsChange(values);
  //     }
  //   }
  // }


  holdBooking():void{
    const request = new Event();
    request.seats = [];
     console.log(this.selectedItems.forEach((s:number)=>s))
    this.selectedItems.forEach((s:number)=>this.getTicket1(s-1).subscribe(
  result=>{
    console.log(this.seat);
    console.log(s-1);
    
  }
))
this.showBooking();
    }

    showBooking(){
        this.paymentDiv=true;
    }

    getTotal(): number {
      let sum = 0;
      this.selectedItems.forEach(item => {
        // @ts-ignore
        sum += this.eventdetails.seats[item-1].seatPrice;
      });
      return sum;
    }

    getTicket1(s:number){
      return this.httpClient.get<any>(this.viewEventurl+"/" + "22b8f962-a9ad-49da-b531-337fa8592d72/"+s)
    }
  
  ngOnInit(): void {
    let id = this.ActivatedRoute.snapshot.paramMap.get('id');
this.url=id;
  
this.display(this.url);
setTimeout(() => {
  document.documentElement.style.setProperty("--colNum", this.eventdetails.seats[1].colm);
}, 500);

}


}