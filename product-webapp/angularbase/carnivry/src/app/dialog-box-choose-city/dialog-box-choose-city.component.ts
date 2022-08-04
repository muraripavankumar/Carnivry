import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dialog-box-choose-city',
  templateUrl: './dialog-box-choose-city.component.html',
  styleUrls: ['./dialog-box-choose-city.component.css']
})
export class DialogBoxChooseCityComponent implements OnInit {

  constructor(private router: Router) { }

  chooseCity(city: any){
    console.log("City clicked "+ city);
    sessionStorage.setItem("city",city);
    this.router.navigate(['']);
    window.location.reload();
  }

  ngOnInit(): void {
  }

}
