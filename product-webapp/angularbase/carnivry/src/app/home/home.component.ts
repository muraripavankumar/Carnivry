import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  name:any;
  email:any;
  constructor(private http: HttpClient,
              private regService: RegistrationService,
              private router: Router) { }

  ngOnInit(): void {
    this.name=this.regService.getName();
    this.email=this.regService.getEmail();
  }

  logout()
  {
    this.regService.logout() .subscribe(() => {
      localStorage.clear();
      this.router.navigate(['/Carnivry/register']);
    });
  }

}
