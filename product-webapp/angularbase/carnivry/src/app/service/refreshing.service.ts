import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RefreshingService {

  constructor() { }

  public notify = new BehaviorSubject<any>('');
  public notifyView=new BehaviorSubject<any>('');

  notifyObservable = this.notify.asObservable();
  notifyViewObservable = this.notifyView.asObservable();

  public notifyOther(data: any) {
    if (data) {
      this.notify.next(data);
    }
  }

  public notifyViewPage(data: any) {
    if (data) {
      console.log('refresh controller');
      this.notifyView.next(data);
    }
  }
  
}
