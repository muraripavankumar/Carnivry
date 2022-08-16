import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RefreshingService } from '../service/refreshing.service';
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
  profilePic: string;
  signIn:string="";
  login:boolean=false;
  constructor(private http: HttpClient,
    private regService: RegistrationService,
    private refreshingService: RefreshingService,
    private registrationService: RegistrationService,
    private router: Router) { }

  ngOnInit(): void {


    this.email = this.regService.getEmail();
    this.authProvider = this.regService.getAuthProvider();

    this.refreshingService.notifyObservable.subscribe(res => {
      if (res.refresh) {
        this.ngOnInit();
      }
    });

    //retrieving profile picture from backend Registration Service
    this.regService.getProfilePic(this.email).subscribe(r => this.profilePic = r);
    if (this.profilePic === '' || this.profilePic === undefined || this.profilePic === null) {
      this.avatarUrl = this.regService.getAvatarUrl();
      this.profilePic = this.avatarUrl;
    }

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

    const tokenValue = localStorage.getItem('token');
    console.log('token value : ' + tokenValue);

    if (tokenValue === null) {
      this.signIn = "SignIn/Login";
      this.login = false;
    }
    else {
      this.signIn = this.registrationService.getName();
      this.login = true;
    }
  }

  SignInLogin() {
    console.log("Button value: " + this.signIn);
    if (this.signIn == "SignIn/Login") {
      this.router.navigate(['/registration/register']);
    }
  }

  logout() {
    this.regService.logout().subscribe(() => {
      localStorage.clear();
      this.ngOnInit();
      window.location.href = "/#/landing-page";
    });
  }

}
