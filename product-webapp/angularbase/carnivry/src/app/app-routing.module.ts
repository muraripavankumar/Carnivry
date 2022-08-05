import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddPreferenceComponent } from './add-preference/add-preference.component';
import { AppComponent } from './app.component';
import { CallbackComponent } from './callback/callback.component';
import { EmailLinkComponent } from './email-link/email-link.component';
import { EmailVerificationComponent } from './email-verification/email-verification.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { HostEventComponent } from './host-event/host-event.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './login/login.component';
import { PostedEventsComponent } from './posted-events/posted-events.component';
import { ProfileComponent } from './profile/profile.component';
import { RegistrationComponent } from './registration/registration.component';
import { SeatingUIComponent } from './seating-ui/seating-ui.component';
import { UpdateEventComponent } from './update-event/update-event.component';
import { ViewPageComponent } from './view-page/view-page.component';

const routes: Routes = [
  {path: "", component: HeaderComponent,  
  children: [
    {
    path: "", component: LandingPageComponent
    },
    {
      path: "view-page/:id", component: ViewPageComponent 
    },
    {
      path: "seat-ui/:id", component: SeatingUIComponent
    },
    {
      path: "host-event", component: HostEventComponent
    }
  ]
    },
    {
      path: "login" , component: LoginComponent
    },
    {
      path: "register", component: RegistrationComponent
    }                                                                          //added by garima
//   {
//   path: 'Carnivry',
//   children: [{
//     path: "",
//     component: AppComponent
//   },
//   {
//     path: "callback",
//     component: CallbackComponent
//   },
//   {
//     path: "home",
//     component: HomeComponent
//   },
//   {
//     path: "register",
//     component: RegistrationComponent
//   },
//   {
//     path: "verifyEmail",
//     component: EmailVerificationComponent
//   },
//   {
//     path: "addPreference",
//     component: AddPreferenceComponent
//   },
//   {
//     path: "login",
//     component: LoginComponent
//   },
//   {
//     path:"emailLink",
//     component:EmailLinkComponent
//   },
//   {
//     path:"forgotpassword",
//     component:ForgotPasswordComponent,
//   },
//   {
//     path: "account",
//     component: ProfileComponent
//   },
//   {
//     path: "host-event",
//     component: HostEventComponent
//   },
//   {
//     path: "update-event",
//     component: UpdateEventComponent
//   },
//   {
//     path:"view-page",
//     component: ViewPageComponent
//   },
//   {
//     path:"seat-ui/:id",
//     component: SeatingUIComponent
//   }
// ]
// },
// {
//   path: '',
//   redirectTo: '/Carnivry/register',
//   pathMatch: 'full'
// }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
