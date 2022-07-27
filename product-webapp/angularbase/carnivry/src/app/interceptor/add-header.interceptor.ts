import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegistrationService } from '../service/registration.service';

@Injectable()
export class AddHeaderInterceptor implements HttpInterceptor {

  constructor(private regService: RegistrationService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const req = request.clone({
      setHeaders: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        Authorization: 'Bearer ' + this.regService.getToken()
      }
    });
    return next.handle(req);
  }
}