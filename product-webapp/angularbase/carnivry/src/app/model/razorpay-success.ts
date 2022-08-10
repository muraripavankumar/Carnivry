import { EventTiming } from "./event-timing";
import { Seat } from "./seat";
import { Venue } from "./venue";

export class RazorpaySuccess {
     orderId:string;
     paymentId:string;
     signature:string;
     eventId:string;
     title:string;
     description:string;
     username:string;
     email:string;
     image:string;
     amount:number;
     host:string;
    venue:Venue;
     timings:EventTiming;
     seats:Seat[];

    

}
