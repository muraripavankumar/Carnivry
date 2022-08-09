import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RazorpayOrder } from '../model/razorpay-order';
import { RazorpaySuccess } from '../model/razorpay-success';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private myClient: HttpClient) { }

  baseUrl= "http://localhost:9000/api/v1";

  createOrder(data: any):Observable<RazorpayOrder>{
    return this.myClient.post<RazorpayOrder>(this.baseUrl+"/create_order",data, {responseType: 'json'});
  }

  paymentSuccess(data:any){
    return this.myClient.post(this.baseUrl+"/payment_success",data, {responseType: 'text'});
  }
}
