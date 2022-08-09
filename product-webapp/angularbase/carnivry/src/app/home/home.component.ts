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

  name: any;
  email: any;
  avatarUrl: any;
  authProvider: any;
  constructor(private http: HttpClient,
    private regService: RegistrationService,
    private router: Router) { }

  ngOnInit(): void {


    this.email = this.regService.getEmail();
    this.authProvider = this.regService.getAuthProvider();

    if (this.authProvider === 'carnivry') {
      this.regService.getProfilePic(this.email).subscribe(res => {
        this.avatarUrl = res;
      }, error => {
        console.log(error);
      })
      this.regService.updateAvatarUrl(this.avatarUrl);
    }
    else
      this.avatarUrl = this.regService.getAvatarUrl();
    setTimeout(() => {
      if (this.avatarUrl === null)
        this.avatarUrl = "../../assets/S.jpg";
      this.name = this.regService.getName();
    }, 100)
  }

  logout() {
    this.regService.logout().subscribe(() => {
      localStorage.clear();
      this.router.navigate(['']);
    });
  }

}
