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
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PastEventsComponent } from './past-events/past-events.component';
import { PostedEventsComponent } from './posted-events/posted-events.component';
import { ProfileComponent } from './profile/profile.component';
import { RegistrationComponent } from './registration/registration.component';
import { SeatingUIComponent } from './seating-ui/seating-ui.component';
import { TicketComponent } from './ticket/ticket.component';
import { UpcomingEventsComponent } from './upcoming-events/upcoming-events.component';
import { UpdateEventComponent } from './update-event/update-event.component';
import { ViewPageComponent } from './view-page/view-page.component';
import { WishlistComponent } from './wishlist/wishlist.component';

const routes: Routes = [
  {


    path: '', component: HeaderComponent,
    children: [
      { path: "", redirectTo: "landing-page", pathMatch: 'full' },
      { path: "landing-page", component: LandingPageComponent },
      { path: 'host-event', component: HostEventComponent },
      { path: 'view-page/:id', component: ViewPageComponent, pathMatch: 'full' },
      { path: 'seat-ui/:id', component: SeatingUIComponent },
      { path: 'account', component: ProfileComponent },
      { path: 'posted-events', component: PostedEventsComponent },
      { path: 'past-events', component: PastEventsComponent },
      { path: 'wishlist', component: WishlistComponent },
      { path: 'upcoming-events', component: UpcomingEventsComponent },
      { path: 'add-preference', component: AddPreferenceComponent },
      { path: 'home', component: HomeComponent },
      { path: 'update', component: UpdateEventComponent },
    ]
  },
  // {path:'add-preference',component:AddPreferenceComponent},
  {
    path: "registration",
    component: AppComponent,
    children: [
      { path: "", redirectTo: "register", pathMatch: 'full' },
      { path: "login", component: LoginComponent },
      { path: "forgot-password", component: ForgotPasswordComponent },
      { path: "register", component: RegistrationComponent },
      { path: 'callback', component: CallbackComponent },
      { path: 'verify-email', component: EmailVerificationComponent }
    ]
  },


  {
    path:'ticket',component:TicketComponent
  },
  {path:"**",component:PageNotFoundComponent}

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }









